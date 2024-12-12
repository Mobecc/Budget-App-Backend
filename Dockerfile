## BUILD Stage ##
FROM gradle:jdk23 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# for all env-variables that we will use in the future:
ENV DB_URL=jdbc:postgresql://dpg-csvnh35umphs73ef68eg-a.frankfurt-postgres.render.com:5432/testname_ko1f
ENV DB_USER=testname_ko1f_user
ENV DB_PASSWORD=${DB_Password}
RUN gradle build --no-daemon

## Package Stage ##
FROM eclipse-temurin:23-jdk
COPY --from=build /home/gradle/src/build/libs/budgetapp-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]