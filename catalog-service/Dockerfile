FROM nimmis/alpine-java:latest
VOLUME /tmp
ADD maven/${project.build.finalName}.jar  /catalog.jar
COPY Calibri-3.jar $JAVA_HOME/lib/ext
CMD ["java", "-Xmx500m","-Xdebug", "-Xrunjdwp:transport=dt_socket,address=5005,server=y,suspend=n", "-jar", "/catalog.jar"]
EXPOSE 5001
EXPOSE 5005
