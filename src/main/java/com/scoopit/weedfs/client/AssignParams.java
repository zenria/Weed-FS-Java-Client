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

public class AssignParams {

    final ReplicationStrategy replicationStrategy;

    final int versionCount;

    final String collection;

    public static final AssignParams DEFAULT = new AssignParams();

    public AssignParams() {
        this(null, 1, null);
    }

    public AssignParams(int versionCount) {
        this(null, versionCount, null);
    }

    public AssignParams(ReplicationStrategy replicationStrategy) {
        this(null, 1, replicationStrategy);
    }

    public AssignParams(String collection, ReplicationStrategy replicationStrategy) {
        this(collection, 1, replicationStrategy);
    }

    public AssignParams(String collection, int versionCount, ReplicationStrategy replicationStrategy) {
        this.collection = collection;
        this.versionCount = versionCount;
        this.replicationStrategy = replicationStrategy;
    }

}
