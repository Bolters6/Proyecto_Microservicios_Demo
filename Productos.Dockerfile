#
# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS build
RUN mkdir /productos
RUN mkdir /productos/lbc
COPY libreria-commons /productos/lbc
RUN mkdir /productos/msp
COPY Microservicio_Productos /productos/msp
WORKDIR /productos/lbc
RUN mvn clean install
WORKDIR /productos/msp
RUN mvn clean install


#
# Package stage
#
FROM openjdk:17-alpine3.14
COPY --from=build /productos/msp/target/productos-0.0.1-SNAPSHOT.jar productos.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "productos.jar"]
