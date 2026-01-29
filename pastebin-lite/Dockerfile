FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY pastebin-lite/pom.xml .
COPY pastebin-lite/mvnw .
COPY pastebin-lite/.mvn .mvn
COPY pastebin-lite/src ./src

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests
RUN mv target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
