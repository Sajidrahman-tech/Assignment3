FROM openjdk:17-jdk-slim
EXPOSE 7000
COPY target/container2-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]