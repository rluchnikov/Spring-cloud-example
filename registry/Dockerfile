FROM nimmis/alpine-java:latest
VOLUME /tmp
ADD maven/${project.build.finalName}.jar  /registry.jar
RUN sh -c 'touch registry.jar'
EXPOSE 8761
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar","/registry.jar"]