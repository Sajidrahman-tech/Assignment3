FROM openjdk:17-jdk-slim
EXPOSE 8000
COPY target/container1-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]