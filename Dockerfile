# Imagem base do OpenJDK
FROM openjdk

# Copia o arquivo JAR da aplicação para o diretório de trabalho
COPY target/carros-1.0.0.jar /app/carros-1.0.0.jar

# Diretório de trabalho dentro do container
WORKDIR /app

# Porta que a aplicação vai usar
EXPOSE 8080

# O comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]

#https://www.youtube.com/watch?v=5QGexrfqu60
	