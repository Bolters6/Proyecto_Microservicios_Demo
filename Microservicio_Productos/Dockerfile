#
# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS build
RUN mkdir /productos
WORKDIR /productos
COPY . /productos
RUN mvn clean install

#
# Package stage
#
FROM openjdk:17-alpine3.14
COPY --from=build /productos/target/productos-0.0.1-SNAPSHOT.jar productos.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "productos.jar"]
