FROM eclipse-temurin:20-jdk

ARG GRADLE_VERSION=8.4

RUN apt-get update && apt-get install -yq make unzip

WORKDIR /app

COPY /config config
COPY /gradle gradle
COPY /build.gradle .
COPY /settings.gradle .
COPY /gradlew .

RUN ./gradlew --no-daemon dependencies

COPY /src src

RUN ./gradlew --no-daemon build

ENV JAVA_OPTS "-Xmx128M -Xms128M"
EXPOSE 8080

CMD java -Dspring.profiles.active=prod -jar build/libs/app-0.0.1-SNAPSHOT.jar
