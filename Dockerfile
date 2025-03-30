# --- Stage 1: Build the application ---
    FROM maven:3.9.6-eclipse-temurin-17 AS builder

    # Set working directory
    WORKDIR /app
    
    # Copy project files
    COPY . /app/
    
    # Ensure Maven wrapper is executable
    RUN chmod +x mvnw
    
    # Build the application (Skip Tests to Speed Up)
    RUN ./mvnw clean package -DskipTests
    
    # --- Stage 2: Create lightweight runtime image ---
    FROM eclipse-temurin:17-jre
    
    # Set working directory
    WORKDIR /app
    
    # Copy JAR from the builder stage
    COPY --from=builder /app/target/*.jar app.jar
    
    # Expose application port
    EXPOSE 8080
    
    # Run the application
    CMD ["java", "-jar", "app.jar"]
    