#
# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS build
RUN mkdir /eureka-server
WORKDIR /eureka-server
COPY . /eureka-server
RUN mvn clean install

#
# Package stage
#
FROM openjdk:17-alpine3.14
COPY --from=build /eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar eureka-server.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]
