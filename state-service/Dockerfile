FROM nimmis/alpine-java:latest
VOLUME /tmp
ADD maven/${project.build.finalName}.jar  /state.jar
CMD ["java", "-Xmx500m","-Xdebug", "-Xrunjdwp:transport=dt_socket,address=5005,server=y,suspend=n", "-jar", "/state.jar"]
EXPOSE 5003
EXPOSE 5005
