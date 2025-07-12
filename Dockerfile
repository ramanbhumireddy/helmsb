FROM eclipse-temurin:21
WORKDIR /app
COPY build/libs/helmsb.jar helmsb.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/helmsb.jar"]
