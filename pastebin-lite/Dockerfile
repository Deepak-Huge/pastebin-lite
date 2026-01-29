FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY pom.xml ./
COPY mvnw ./
COPY .mvn .mvn
COPY src ./src

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

RUN ls -l target/

COPY target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "target/pastebin-lite-1.0.jar"]
