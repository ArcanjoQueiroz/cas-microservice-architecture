#!/bin/bash
mvn archetype:generate \
   -DgroupId=br.com.alexandre \
   -DartifactId=my-service \
   -DarchetypeArtifactId=cas-spring-security-cxf-webapp \
   -DarchetypeGroupId=br.com.alexandre \
   -DinteractiveMode=false
