# ... (Etapa 1 igual) ...
RUN ./gradlew bootJar --no-daemon

# ========================================
# ETAPA 2: RUNTIME (Ejecución)
# ========================================
FROM eclipse-temurin:17-jre-alpine

EXPOSE 8080

# CORRECCIÓN AQUÍ: Usar el nombre real del proyecto 'ExamenMercado'
COPY --from=build ./build/libs/ExamenMercado-1.0-SNAPSHOT.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]