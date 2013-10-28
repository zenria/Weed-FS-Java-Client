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

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class WeedFSClientBuilder {

    HttpClient httpClient;

    URL masterUrl;

    public WeedFSClientBuilder() {

    }

    public static WeedFSClientBuilder createBuilder() {
        return new WeedFSClientBuilder();
    }

    public WeedFSClientBuilder setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
        return this;
    }

    public WeedFSClientBuilder setMasterUrl(URL masterUrl) {
        this.masterUrl = masterUrl;
        return this;
    }

    public WeedFSClient build() {
        if (masterUrl == null) {
            try {
                // default url for testing purpose
                masterUrl = new URL("http://localhost:9333");
            } catch (MalformedURLException e) {
                // This cannot happen by construction
                throw new Error(e);
            }
        }

        if (httpClient == null) {
            // minimal http client
            httpClient = HttpClientBuilder.create().build();
        }

        return new WeedFSClientImpl(masterUrl, httpClient);
    }

}
