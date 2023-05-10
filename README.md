# java-pg-docker

Build with:
- Java JDK 17
- Postgres 12
- Maven
- Spring boot 3
- Docker

Setup
- clone this repository
- run "mvn clean install"

Run via docker
- run "mvn clean package -DskipTests"
- run "docker compose up user_app"


Run via terminal
- just run the main APP -> UserAppApplication


How to see API:
- open this link in your browser : http://localhost:8080/swagger-ui/index.html#/
