# Multi-Stage Dockerfile

# Build Stage
FROM gradle:jdk23 as build
WORKDIR /home/gradle/src
# Kopiere den gesamten Code ins Image
COPY . .
RUN gradle build --no-daemon  # Baue das Projekt

# Runtime Stage
# Leichtes Java-Image
FROM eclipse-temurin:23-jdk-alpine
WORKDIR /app
# Kopiere die erstellte JAR-Datei
COPY --from=build /home/gradle/src/build/libs/budgetapp-0.0.1-SNAPSHOT.jar app.jar
# Exponiere Port 8080
EXPOSE 8080
# Starte die Anwendung
ENTRYPOINT ["java", "-jar", "app.jar"]