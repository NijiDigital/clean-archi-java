# Build stage
FROM gradle:8.8-jdk21 AS builder

WORKDIR /build

# Copy gradle files first for better caching
COPY app/build.gradle ./app/
COPY settings.gradle ./
COPY gradlew ./
COPY gradle/ ./gradle/

# Download dependencies
RUN ./gradlew :app:dependencies --no-daemon

# Copy source code
COPY app/ ./app/

# Build the application
RUN ./gradlew :app:bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

# Create non-root user
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

WORKDIR /app

# Copy the built jar from builder stage
COPY --from=builder /build/app/build/libs/*.jar app.jar

# Change ownership to non-root user
RUN chown -R appuser:appgroup /app

USER appuser

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"] 