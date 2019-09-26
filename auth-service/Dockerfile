FROM nimmis/alpine-java:latest
VOLUME /tmp
ADD maven/${project.build.finalName}.jar  /auth.jar
CMD ["java", "-Xmx200m", "-jar", "/auth.jar"]

EXPOSE 5000