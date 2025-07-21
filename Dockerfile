# Imagen base con JDK 17
FROM maven:3.8.1-openjdk-17 AS build

# Directorio de trabajo
WORKDIR /workspace/app

# Copiar archivos Maven
COPY pom.xml .
COPY src src

# Construir la aplicación sin tests
RUN mvn clean package -DskipTests

# Imagen final más ligera
FROM eclipse-temurin:17-jre-alpine

# Crear un usuario no root
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Directorio donde se ejecutará la aplicación
WORKDIR /app

# Copiar el JAR de la etapa de construcción
COPY --from=build /workspace/app/target/*.jar app.jar

# Cambiar propietario de los archivos
RUN chown -R appuser:appgroup /app

# Cambiar a usuario no privilegiado
USER appuser

# Configurar para recibir el puerto a través de variable de entorno
ENV PORT=8080

# Exponer el puerto
EXPOSE ${PORT}

# Healthcheck para Docker y Prometheus
HEALTHCHECK --interval=30s --timeout=3s --retries=3 CMD wget -q --spider http://localhost:${PORT}/actuator/health || exit 1

# Comando para ejecutar la aplicación
CMD ["sh", "-c", "java -Dserver.port=${PORT} -jar app.jar"]