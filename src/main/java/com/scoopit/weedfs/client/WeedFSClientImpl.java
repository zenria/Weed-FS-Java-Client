/*
 * (C) Copyright 2013 Scoop IT SAS (http://scoop.it/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Contributors:
 *     Philippe GASSMANN
 *     Jean-Baptiste BELLET
 */
package com.scoopit.weedfs.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scoopit.weedfs.client.caching.LookupCache;
import com.scoopit.weedfs.client.net.AssignResult;
import com.scoopit.weedfs.client.net.LookupResult;
import com.scoopit.weedfs.client.net.WriteResult;

class WeedFSClientImpl implements WeedFSClient {

    final URL masterURL;
    final HttpClient httpClient;
    final LookupCache lookupCache;

    WeedFSClientImpl(URL masterURL, HttpClient httpClient, LookupCache lookupCache) {
        this.masterURL = masterURL;
        this.httpClient = httpClient;
        this.lookupCache = lookupCache;
    }

    @Override
    public Assignation assign(AssignParams params) throws IOException, WeedFSException {
        StringBuilder url = new StringBuilder(new URL(masterURL, "/dir/assign").toExternalForm());
        url.append("?count=");
        url.append(params.versionCount);
        if (params.replicationStrategy != null) {
            url.append("&replication=");
            url.append(params.replicationStrategy.parameterValue);
        }

        HttpGet get = new HttpGet(url.toString());
        try {
            HttpResponse response = httpClient.execute(get);

            ObjectMapper mapper = new ObjectMapper();
            try {
                AssignResult result = mapper.readValue(response.getEntity().getContent(), AssignResult.class);

                if (result.error != null) {
                    throw new WeedFSException(result.error);
                }

                return new Assignation(result);
            } catch (JsonMappingException | JsonParseException e) {
                throw new WeedFSException(e);
            }
        } finally {
            get.abort();
        }
    }

    @Override
    public void delete(WeedFSFile file, Location location) throws IOException, WeedFSException {
        StringBuilder url = new StringBuilder();
        if (!location.url.contains("http")) {
            url.append("http://");
        }
        url.append(location.url);
        url.append("/");
        url.append(file.fid);

        HttpDelete delete = new HttpDelete(url.toString());
        try {
            HttpResponse response = httpClient.execute(delete);

            StatusLine line = response.getStatusLine();
            if (line.getStatusCode() < 200 || line.getStatusCode() > 299) {
                throw new WeedFSException("Error deleting file " + file.fid + " on " + location.url + ": " + line.getStatusCode() + " "
                        + line.getReasonPhrase());
            }
        } finally {
            delete.abort();
        }
    }

    @Override
    public List<Location> lookup(long volumeId) throws IOException, WeedFSException {
        if (lookupCache != null) {
            List<Location> ret = lookupCache.lookup(volumeId);
            if (ret != null) {
                return ret;
            }
        }

        StringBuilder url = new StringBuilder(new URL(masterURL, "/dir/lookup").toExternalForm());
        url.append("?volumeId=");
        url.append(volumeId);

        HttpGet get = new HttpGet(url.toString());
        try {
            HttpResponse response = httpClient.execute(get);

            ObjectMapper mapper = new ObjectMapper();
            try {
                LookupResult result = mapper.readValue(response.getEntity().getContent(), LookupResult.class);

                if (result.error != null) {
                    throw new WeedFSException(result.error);
                }

                if(lookupCache!=null){
                    lookupCache.setLocation(volumeId, result.locations);
                }
                
                return result.locations;
            } catch (JsonMappingException | JsonParseException e) {
                throw new WeedFSException(e);
            }
        } finally {
            get.abort();
        }

    }

    @Override
    public int write(WeedFSFile file, Location location, File fileToUpload) throws IOException, WeedFSException {
        if (fileToUpload.length() == 0) {
            throw new WeedFSException("Cannot write a 0-length file");
        }
        StringBuilder url = new StringBuilder();
        if (!location.url.contains("http")) {
            url.append("http://");
        }
        url.append(location.url);
        url.append('/');
        url.append(file.fid);

        if (file.version > 0) {
            url.append('_');
            url.append(file.version);
        }

        HttpPost post = new HttpPost(url.toString());
        
        post.setEntity(MultipartEntityBuilder.create().addBinaryBody("file", fileToUpload).build());
        try {
            HttpResponse response = httpClient.execute(post);
            ObjectMapper mapper = new ObjectMapper();
            try {
                WriteResult result = mapper.readValue(response.getEntity().getContent(), WriteResult.class);

                if (result.error != null) {
                    throw new WeedFSException(result.error);
                }

                return result.size;
            } catch (JsonMappingException | JsonParseException e) {
                throw new WeedFSException(e);
            }
        } finally {
            post.abort();
        }
    }

    @Override
    public InputStream read(WeedFSFile file, Location location) throws IOException, WeedFSException, WeedFSFileNotFoundException {
        StringBuilder url = new StringBuilder();
        if (!location.url.contains("http")) {
            url.append("http://");
        }
        url.append(location.url);
        url.append('/');
        url.append(file.fid);

        if (file.version > 0) {
            url.append('_');
            url.append(file.version);
        }
        HttpGet get = new HttpGet(url.toString());
        HttpResponse response = httpClient.execute(get);
        StatusLine line = response.getStatusLine();
        if (line.getStatusCode() == 404) {
            get.abort();
            throw new WeedFSFileNotFoundException(file, location);
        }
        if (line.getStatusCode() != 200) {
            get.abort();
            throw new WeedFSException("Error reading file " + file.fid + " on " + location.url + ": " + line.getStatusCode() + " "
                    + line.getReasonPhrase());
        }
        return response.getEntity().getContent();
    }

}
