# Usar una imagen oficial de Java 11
FROM openjdk:11-jre-slim

# Crear un directorio en el contenedor para la aplicación
WORKDIR /app

# Copiar el archivo .jar desde tu proyecto a ese directorio en el contenedor
COPY build/libs/*.jar app.jar

# Exponer el puerto en el que tu aplicación Spring Boot correrá
EXPOSE 8080

# Ejecutar la aplicación Spring Boot cuando el contenedor se inicie
ENTRYPOINT ["java", "-jar", "app.jar"]
