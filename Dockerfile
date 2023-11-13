FROM eclipse-temurin:17-jdk-ubi9-minimal

WORKDIR /opt/app

COPY . .

CMD ["build", "gradle"]

FROM eclipse-temurin:17-jre-ubi9-minimal

WORKDIR /opt/app

COPY --from=0 /opt/app/build/libs/covid-api-0.0.1-SNAPSHOT.jar /app.jar

CMD ["java", "-jar", "app.jar"]