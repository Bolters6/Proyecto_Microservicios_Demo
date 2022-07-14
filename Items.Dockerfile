#
# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS build
RUN mkdir /items
RUN mkdir /items/lbc
COPY libreria-commons /items/lbc
RUN mkdir /items/msi
COPY Microservicio_Items /items/msi
WORKDIR /items/lbc
RUN mvn clean install
WORKDIR /items/msi
RUN mvn clean install -D skipTests


#
# Package stage
#
FROM openjdk:17-alpine3.14
COPY --from=build /items/msi/target/items-0.0.1-SNAPSHOT.jar items.jar
EXPOSE 8007
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "items.jar"]
