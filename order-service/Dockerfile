FROM nimmis/alpine-java:latest
VOLUME /tmp
ADD maven/${project.build.finalName}.jar  /order.jar
CMD ["java", "-Xmx500m","-Xdebug", "-Xrunjdwp:transport=dt_socket,address=5005,server=y,suspend=n", "-jar", "/order.jar"]
EXPOSE 5002
EXPOSE 5005