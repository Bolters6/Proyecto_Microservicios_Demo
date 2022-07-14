#
# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS build
RUN mkdir /usuarios
RUN mkdir /usuarios/lbuc
COPY libreria-usuarios-commons /usuarios/lbuc
RUN mkdir /usuarios/msu
COPY microservicio_usuarios /usuarios/msu
WORKDIR /usuarios/lbuc
RUN mvn clean install
WORKDIR /usuarios/msu
RUN mvn clean install

#
# Package stage
#
FROM openjdk:17-alpine3.14
COPY --from=build /usuarios/msu/target/microservicio_usuarios-0.0.1-SNAPSHOT.jar usuarios.jar
EXPOSE 8088
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "usuarios.jar"]
