# =================
# STAGE 1: Build
# =================
# Use a Maven and Java 21 image to build the application's JAR file.
FROM maven:3.9-eclipse-temurin-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project into the container
COPY . .

# Run the Maven package command to build the project and create the JAR file.
# We skip tests here because they've already been run. This makes the build faster.
RUN mvn clean package -DskipTests

# =================
# STAGE 2: Run
# =================
# Use a minimal Java 21 Runtime image. This makes our final image much smaller and more secure.
FROM eclipse-temurin:21-jre-jammy

# Set the working directory
WORKDIR /app

# Copy ONLY the built JAR file from the 'build' stage into this new, clean stage.
COPY --from=build /app/target/*.jar app.jar

# Expose port 25565. This tells Docker that the application inside the container will listen on this port.
EXPOSE 25565

# The command to run when the container starts.
# This executes your Spring Boot application.
ENTRYPOINT ["java", "-jar", "app.jar"]