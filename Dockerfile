FROM openjdk:latest
ARG JAR_FILE=target/Stockify-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} Stockify.jar
ENTRYPOINT ["java","-jar","Stockify.jar"]