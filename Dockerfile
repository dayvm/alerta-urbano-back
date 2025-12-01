# --- Estágio 1: Build ---
# Usamos uma imagem Maven com Java 17 (compatível com Spring Boot 3)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia os arquivos do projeto
COPY . .

# Faz o build (gera o arquivo .jar na pasta target)
RUN mvn clean package -DskipTests

# --- Estágio 2: Execução ---
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# AQUI ESTAVA O ERRO: Agora usamos o nome exato do seu projeto
COPY --from=build /app/target/Alert-City-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]