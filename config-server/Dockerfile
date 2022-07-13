#
# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS build
RUN mkdir /config-server
WORKDIR /config-server
COPY . /config-server
RUN mvn clean install

#
# Package stage
#
FROM openjdk:17-alpine3.14
COPY --from=build /config-server/target/config-server-0.0.1-SNAPSHOT.jar config-server.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "config-server.jar"]
