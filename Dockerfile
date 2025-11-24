# ========================================
# ETAPA 1: BUILD (Compilación)
# ========================================
FROM alpine:latest as build

RUN apk update
RUN apk add openjdk17
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew bootJar --no-daemon

# ========================================
# ETAPA 2: RUNTIME (Ejecución)
# ========================================
# CAMBIO AQUÍ: Usamos eclipse-temurin en lugar de openjdk
FROM eclipse-temurin:17-jre-alpine

EXPOSE 8080

COPY --from=build ./build/libs/Mutantes-1.0-SNAPSHOT.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]