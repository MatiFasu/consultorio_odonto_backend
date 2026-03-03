# Etapa 1: Construcción
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
# Copiamos el archivo pom.xml para descargar las dependencias (mejor cacheo)
COPY pom.xml .
RUN mvn dependency:go-offline -B
# Copiamos el código fuente y compilamos el proyecto, saltando los tests para agilizar
COPY src ./src
RUN mvn package -DskipTests

# Etapa 2: Ejecución (Runtime)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copiamos solo el JAR resultante de la etapa anterior
COPY --from=build /app/target/consultorioOdontologico-0.0.1.jar app_consultorio.jar
EXPOSE 8080
# Comando de arranque
ENTRYPOINT ["java", "-jar", "app_consultorio.jar"]
