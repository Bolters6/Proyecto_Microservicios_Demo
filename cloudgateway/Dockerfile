#
# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS build
RUN mkdir /cloudgateway
WORKDIR /cloudgateway
COPY . /cloudgateway
RUN mvn clean install

#
# Package stage
#
FROM openjdk:17-alpine3.14
COPY --from=build /cloudgateway/target/cloudgateway-0.0.1-SNAPSHOT.jar cloudgateway.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "cloudgateway.jar"]
