FROM openjdk:11.0.15-jre-slim
WORKDIR /opt
COPY target/tracker*.jar tracker.jar
EXPOSE 8080/tcp
ENTRYPOINT [ "java", "-jar", "tracker.jar" ]