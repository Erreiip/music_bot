FROM openjdk:21-slim

WORKDIR /app

COPY discord_bot-1.0-SNAPSHOT.jar .

COPY init.sh .
RUN chmod +x init.sh

CMD ["./init.sh"]