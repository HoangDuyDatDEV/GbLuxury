FROM openjdk:16-alpine
WORKDIR /
COPY target/GbLurxury-0.0.1-SNAPSHOT.jar GbLurxury-0.0.1-SNAPSHOT.jar
EXPOSE 8081
CMD ["java", "-jar", "GbLurxury-0.0.1-SNAPSHOT.jar"]
