FROM openjdk:17-alpine
COPY target/user-app-0.0.1-SNAPSHOT.jar app-1.0.0.jar
#ENTRYPOINT ["java" ,"-Djava.security.egd=file:/dev/./urandom --spring.config.location=classpath:file:/app/application-properties","-jar","/app/app-1.0.0.jar"]
ENTRYPOINT [ "java", "-jar", "app-1.0.0.jar" ]