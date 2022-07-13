#
# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS build
RUN mkdir /oauth-server
WORKDIR /oauth-server
COPY . /oauth-server
RUN mvn clean install

#
# Package stage
#
FROM openjdk:17-alpine3.14
COPY --from=build /oauth-server/target/oauth-server-0.0.1-SNAPSHOT.jar oauth-server.jar
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "oauth-server.jar"]
