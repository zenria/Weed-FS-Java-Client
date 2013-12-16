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
import java.util.List;

public class WeedFSClientMock implements WeedFSClient {

    @Override
    public Assignation assign(AssignParams params) throws IOException, WeedFSException {
        return null;
    }

    @Override
    public int write(WeedFSFile weedFSFile, Location location, File file) throws IOException, WeedFSException {
        return 0;
    }

    @Override
    public int write(WeedFSFile file, Location location, byte[] dataToUpload, String fileName) throws IOException,
            WeedFSException {
        return 0;
    }

    @Override
    public int write(WeedFSFile file, Location location, InputStream inputToUpload, String fileName)
            throws IOException, WeedFSException {
        return 0;
    }

    @Override
    public void delete(WeedFSFile file, Location location) throws IOException, WeedFSException {
    }

    @Override
    public List<Location> lookup(long volumeId) throws IOException, WeedFSException {
        return null;
    }

    @Override
    public InputStream read(WeedFSFile file, Location location) throws IOException, WeedFSException {
        return null;
    }

}
