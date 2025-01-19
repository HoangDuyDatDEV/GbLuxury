FROM openjdk:16-alpine
WORKDIR /
COPY target/GB-0.0.1-SNAPSHOT.jar GB-0.0.1-SNAPSHOT.jar
EXPOSE 8081
CMD ["java", "-jar", "GB-0.0.1-SNAPSHOT.jar"]
