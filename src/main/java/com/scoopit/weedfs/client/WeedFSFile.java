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

public class WeedFSFile {

    public final String fid;

    public int version = 0;

    public WeedFSFile(String fid) {
        this.fid = fid;
    }

    public long getVolumeId() {
        int pos = fid.indexOf(',');
        if (pos == -1) {
            throw new IllegalArgumentException("Cannot parse fid: " + fid);
        }
        try {
            return Long.parseLong(fid.substring(0, pos));
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Cannot parse fid: " + fid, nfe);
        }
    }

    @Override
    public String toString() {
        return "WeedFSFile [fid=" + fid + ", version=" + version + "]";
    }

}
