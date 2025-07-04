# ---- Stage 1: Build ----
FROM openjdk:17-jdk-slim AS builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw package -DskipTests


# ---- Stage 2: Run ----
FROM openjdk:17-jdk-slim
WORKDIR /app

# Explicitly copy the keystore into the container's filesystem.
# This is a new, important line.
COPY src/main/resources/keystore.p12 /app/keystore.p12

# Copy the built .jar file from the 'builder' stage.
COPY --from=builder /app/target/*.jar app.jar

# Expose port 25565.
EXPOSE 25565

# The command to run when the container starts.
# We override the keystore location with a direct file path.
# This is the other important change.
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=dev", "--server.ssl.key-store=/app/keystore.p12"]