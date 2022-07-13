#
# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS build
RUN mkdir /libreria-usuario-commons
WORKDIR /libreria-usuario-commons
COPY . /libreria-usuario-commons
RUN mvn clean install

#
# Package stage
#
FROM openjdk:17-alpine3.14
COPY --from=build /libreria-usuario-commons/target/libreria-usuario-commons-0.0.1-SNAPSHOT.jar libreria-usuario-commons.jar
ENTRYPOINT ["java", "-jar", "libreria-usuario-commons.jar"]
