FROM openjdk:8-jre-alpine
MAINTAINER Andre Formento <andreformento.sc@gmail.com>

ENV CLIENT_ID CLIENT_ID
ENV CLIENT_SECRET CLIENT_SECRET

VOLUME /tmp
ADD *.jar app.jar
ENV JAVA_OPTS=""

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
