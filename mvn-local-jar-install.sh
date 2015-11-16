
#!/bin/bash
mouseLocalLibPath="/Users/Toshi/server/mouse/WEB-INF/lib/"
localRepositoryPath="/Volumes/share-2/m2/repository"
mvn install:install-file -Dfile=${mouseLocalLibPath}weed-fs-client-1.0.4.jar -DgroupId=com.scoopit -DartifactId=weed-fs-client -Dversion=1.0.4 -Dpackaging=jar -DlocalRepositoryPath=${localRepositoryPath}
