# Use a base JDK image
FROM openjdk:24-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the built JAR (you'll build it first with `mvn package` or `gradle build`)
COPY target/your-app-name.jar app.jar

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
