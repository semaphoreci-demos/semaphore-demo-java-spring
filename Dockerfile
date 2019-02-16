FROM openjdk:8-jdk-alpine
ARG ENVIRONMENT
ENV ENVIRONMENT ${ENVIRONMENT}
COPY target/*.jar app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=${ENVIRONMENT}", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]