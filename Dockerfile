# ========================================
# ETAPA 1: BUILD (Compilación)
# ========================================
FROM alpine:latest as build

# Instalar Java 17 para compilar
RUN apk update
RUN apk add openjdk17

# Copiar archivos
COPY . .
RUN chmod +x ./gradlew

# Compilar el proyecto
RUN ./gradlew bootJar --no-daemon

# ========================================
# ETAPA 2: RUNTIME (Ejecución)
# ========================================
# Usamos la imagen correcta y mantenida de Java 17
FROM eclipse-temurin:17-jre-alpine

EXPOSE 8080

# Copiamos el JAR generado.
# IMPORTANTE: Usamos 'ExamenMercado' porque así se llama en settings.gradle
COPY --from=build ./build/libs/ExamenMercado-1.0-SNAPSHOT.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]