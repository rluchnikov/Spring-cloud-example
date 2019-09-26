FROM nimmis/alpine-java:latest
VOLUME /tmp
ADD maven/${project.build.finalName}.jar  /config.jar
CMD ["java", "-Xmx200m", "-jar", "/config.jar"]

HEALTHCHECK --interval=10s --timeout=3s CMD curl -f http://localhost:8888/health || exit 1

EXPOSE 8888