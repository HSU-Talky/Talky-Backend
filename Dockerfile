FROM eclipse-temurin:21-jre

WORKDIR /app

COPY build/libs/*.jar /app/app.jar

ENV TZ=Asia/Seoul

ENTRYPOINT ["java","-jar","/app/app.jar"]


