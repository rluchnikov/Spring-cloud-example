FROM nimmis/alpine-java:latest
VOLUME /tmp
ADD maven/${project.build.finalName}.jar  /gateway.jar
RUN sh -c 'touch gateway.jar'
EXPOSE 8761
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar","/gateway.jar"]