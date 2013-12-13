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

/**
 * <pre>
 *                      ,
 *                     dM
 *                     MMr
 *                    4MMML                  .
 *                    MMMMM.                xf
 *    .              "M6MMM               .MM-
 *     Mh..          +MM5MMM            .MMMM
 *     .MMM.         .MMMMML.          MMMMMh
 *      )MMMh.        MM5MMM         MMMMMMM
 *       3MMMMx.     'MMM3MMf      xnMMMMMM"
 *       '*MMMMM      MMMMMM.     nMMMMMMP"
 *         *MMMMMx    "MMM5M\    .MMMMMMM=
 *          *MMMMMh   "MMMMM"   JMMMMMMP
 *            MMMMMM   GMMMM.  dMMMMMM            .
 *             MMMMMM  "MMMM  .MMMMM(        .nnMP"
 *  ..          *MMMMx  MMM"  dMMMM"    .nnMMMMM*
 *   "MMn...     'MMMMr 'MM   MMM"   .nMMMMMMM*"
 *    "4MMMMnn..   *MMM  MM  MMP"  .dMMMMMMM""
 *      ^MMMMMMMMx.  *ML "M .M*  .MMMMMM**"
 *         *PMMMMMMhn. *x > M  .MMMM**""
 *            ""**MMMMhx/.h/ .=*"
 *                     .3P"%....
 *                   nP"     "*MMnx
 * 
 * </pre>
 * 
 */
public interface WeedFSClient {
    Assignation assign(AssignParams params) throws IOException, WeedFSException;

    int write(WeedFSFile weedFSFile, Location location, File file) throws IOException, WeedFSException;

    int write(WeedFSFile file, Location location, byte[] dataToUpload) throws IOException, WeedFSException;

    int write(WeedFSFile file, Location location,  InputStream inputToUpload) throws IOException, WeedFSException;

    void delete(WeedFSFile file, Location location) throws IOException, WeedFSException;

    List<Location> lookup(long volumeId) throws IOException, WeedFSException;

    InputStream read(WeedFSFile file, Location location) throws IOException, WeedFSException;

}
