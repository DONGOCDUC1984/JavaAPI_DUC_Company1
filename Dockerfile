#This means: "Start my Docker image with a Linux environment that already contains Java 17."
FROM eclipse-temurin:17-jre
#This means: This creates/uses /app inside the Docker container.
#So later: "COPY target/*.jar app.jar" means the JAR will be copied into: /app/app.jar
WORKDIR /app

COPY target/*.jar app.jar
# This means: This container is intended to provide a service on port 8080.
EXPOSE 8080
#This means:tells Docker to launch your packaged Spring Boot application whenever the container starts.
ENTRYPOINT ["java", "-jar", "app.jar"]