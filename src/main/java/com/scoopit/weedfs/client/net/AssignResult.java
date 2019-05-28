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
package com.scoopit.weedfs.client.net;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.scoopit.weedfs.client.Location;
import com.scoopit.weedfs.client.WeedFSFile;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AssignResult extends Result {
    public int count;
    public String fid;
    public String publicUrl;
    public String url;

    public Location getLocation() {
        Location ret = new Location();
        ret.publicUrl = publicUrl;
        ret.url = url;
        return ret;
    }

    public WeedFSFile getWeedFSFile() {
        return new WeedFSFile(fid);
    }

    public int getCount() {
        return count;
    }

}
