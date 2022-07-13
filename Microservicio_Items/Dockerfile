#
# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS build
RUN mkdir /items
WORKDIR /items
COPY . /items
RUN mvn clean install

#
# Package stage
#
FROM openjdk:17-alpine3.14
COPY --from=build /items/target/items-0.0.1-SNAPSHOT.jar items.jar
EXPOSE 8005
ENTRYPOINT ["java", "-jar", "items.jar"]
