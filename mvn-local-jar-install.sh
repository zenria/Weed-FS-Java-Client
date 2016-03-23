
#!/bin/bash
localLibPath="/Users/Toshi/server/Weed-FS-Java-Client/target/"
localRepositoryPath="/Volumes/share-2/m2/repository"
mvn install:install-file -Dfile=${localLibPath}weed-fs-client-1.0.5.jar -DgroupId=com.scoopit -DartifactId=weed-fs-client -Dversion=1.0.5 -Dpackaging=jar 
