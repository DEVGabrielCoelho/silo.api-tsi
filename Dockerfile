# Use a imagem oficial do Tomcat 10 com JDK 17
FROM tomcat:10.0-jdk17

# Define um argumento para o arquivo WAR
ARG WAR_FILE=target/siloapi.war

# Copie o arquivo WAR para o diretório webapps do Tomcat
COPY ${WAR_FILE} /usr/local/tomcat/webapps/ROOT.war

# Exponha a porta 8080 para acesso externo
EXPOSE 8080

# Comando para iniciar o Tomcat
CMD ["catalina.sh", "run"]
