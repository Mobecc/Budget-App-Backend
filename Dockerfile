## BUILD Stage ##
FROM gradle:jdk23 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# for all env-variables that we will use in the future:
ENV DB_URL=${DB_URL}
ENV DB_USER=${DB_Username}
ENV DB_PASSWORD=${DB_Password}
RUN gradle build --no-daemon

## Package Stage ##
FROM eclipse-temurin:23-jdk
COPY --from=build /home/gradle/src/build/libs/budgetapp-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]