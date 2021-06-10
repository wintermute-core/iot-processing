FROM adoptopenjdk/openjdk11:x86_64-tumbleweed-jdk-11.0.11_9-slim

RUN mkdir /app
ADD build/libs/*.jar /app/

WORKDIR /app

ENTRYPOINT java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/*.jar --spring.config.location=/app/application.yaml
