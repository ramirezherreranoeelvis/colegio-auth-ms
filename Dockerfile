# Etapa de compilación
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /workspace/app

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw clean package -DskipTests

# Etapa final de runtime (producción)
FROM eclipse-temurin:17-jre-alpine

VOLUME /tmp
RUN apk add --no-cache curl

COPY --from=build /workspace/app/target/*.jar /app.jar

RUN addgroup --system --gid 1001 appgroup && \
    adduser --system --uid 1001 --ingroup appgroup appuser && \
    chown appuser:appgroup /app.jar
USER appuser

EXPOSE 8090

#HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
#    CMD curl -f http://localhost:8090/actuator/health || exit 1

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
