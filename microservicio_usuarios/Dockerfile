#
# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS build
RUN mkdir /microservicio-usuarios
WORKDIR /microservicio-usuarios
COPY . /microservicio-usuarios
RUN mvn clean install

#
# Package stage
#
FROM openjdk:17-alpine3.14
COPY --from=build /microservicio-usuarios/target/microservicio-usuarios-0.0.1-SNAPSHOT.jar microservicio-usuarios.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "microservicio-usuarios.jar"]
