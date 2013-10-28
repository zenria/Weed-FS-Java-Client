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

public enum ReplicationStrategy {
    /**
     * no replication
     */
    None("000"),
    /**
     * replicate once on the same rack
     */
    OnceOnSameRack("001"),
    /**
     * replicate once on a different rack, but same data center
     */
    OnceOnDifferentRack("010"),
    /**
     * replicate once on a different data center
     */
    OnceOnDifferentDC("100"),
    /**
     * replicate twice on two different data center
     */
    TwiceOnDifferentDC("200"),
    /**
     * replicate once on a different rack, and once on a different data center
     */
    OnceOnDifferentRackAndOnceOnDifferentDC("110");

    private ReplicationStrategy(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    final String parameterValue;

}
