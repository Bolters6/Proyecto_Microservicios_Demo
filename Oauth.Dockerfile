#
# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS build
RUN mkdir /oauth
RUN mkdir /oauth/lbuc
COPY libreria-usuarios-commons /oauth/lbuc
RUN mkdir /oauth/msos
COPY oauth_server /oauth/msos
WORKDIR /oauth/lbuc
RUN mvn clean install
WORKDIR /oauth/msos
RUN mvn clean install


#
# Package stage
#
FROM openjdk:17-alpine3.14
COPY --from=build /oauth/msos/target/oauth_server-0.0.1-SNAPSHOT.jar oauth.jar
EXPOSE 8087
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "oauth.jar"]
