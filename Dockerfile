FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

RUN ./gradlew dependencies --no-deamon || true

COPY src src

EXPOSE 8080

CMD ["./gradlew", "bootRun", "--continuous"]
