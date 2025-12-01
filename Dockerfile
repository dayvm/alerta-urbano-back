# Estágio de Build
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
# AQUI ESTÁ O TRUQUE: Adicionamos -DfinalName=app
# Isso força o arquivo gerado a se chamar sempre "app.jar", independente do nome no pom.xml
RUN mvn clean package -DskipTests -DfinalName=app

# Estágio de Execução
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
# Agora copiamos o arquivo que sabemos que se chama app.jar
COPY --from=build /app/target/app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
