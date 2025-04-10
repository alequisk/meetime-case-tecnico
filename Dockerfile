FROM maven:3.9-amazoncorretto-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn package -DskipTests


FROM amazoncorretto:17-alpine
WORKDIR /app

ARG APP_PORT=8080
ENV SERVER_PORT=${APP_PORT}

COPY --from=build /app/target/*.jar app.jar

EXPOSE ${APP_PORT}

ENTRYPOINT ["java", "-jar", "app.jar"]