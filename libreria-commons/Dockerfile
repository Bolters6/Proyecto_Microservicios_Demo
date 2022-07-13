#
# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS build
RUN mkdir /libreria-commons
WORKDIR /libreria-commons
COPY . /libreria-commons
RUN mvn clean install

#
# Package stage
#
FROM openjdk:17-alpine3.14
COPY --from=build /libreria-commons/target/libreria-commons-0.0.1-SNAPSHOT.jar libreria-commons.jar
ENTRYPOINT ["java", "-jar", "libreria-commons.jar"]
