FROM eclipse-temurin:20-jdk

ARG GRADLE_VERSION=8.4

RUN apt-get update && apt-get install -yq make unzip

WORKDIR /app

COPY /app/gradle gradle
COPY /app/build.gradle .
COPY /app/settings.gradle .
COPY /app/gradlew .

RUN ./gradlew --no-daemon dependencies

COPY /app/src src

RUN ./gradlew --no-daemon build

ENV JAVA_OPTS "-Xmx128M -Xms128M"
EXPOSE 8080

CMD java -jar build/libs/app-1.0-SNAPSHOT.jar
