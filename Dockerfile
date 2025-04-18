# Step 1: Use an official Gradle image to build the project
FROM gradle:jdk21 AS builder

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the Gradle wrapper and build files
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Step 4: Copy the rest of the project files
COPY src ./src

# Step 5: Build the application
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test


# Step 6: Use an official OpenJDK runtime image
FROM openjdk:21-jdk-slim

# Step 7: Set the working directory inside the container
WORKDIR /app

# Step 8: Install tzdata and set timezone
RUN apt-get update && apt-get install -y tzdata && \
    ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime && \
    echo "Asia/Seoul" > /etc/timezone && \
    apt-get clean

# Step 9: Copy the jar file from the build stage
COPY --from=builder /app/build/libs/app.jar app.jar

# Step 10: Expose the application port
EXPOSE 8080

# Step 11: Run the application
ENTRYPOINT ["java", "-Dcom.zaxxer.hikari.blockUntilFilled=true", "-jar", "app.jar"]
