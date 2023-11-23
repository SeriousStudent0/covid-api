FROM eclipse-temurin:17-jdk-ubi9-minimal AS builder

WORKDIR /opt/app

# Install necessary dependencies
RUN microdnf install -y unzip && \
    microdnf clean all

# Install Gradle
RUN curl -L https://services.gradle.org/distributions/gradle-7.3-bin.zip -o gradle.zip && \
    unzip gradle.zip && \
    rm gradle.zip && \
    mv gradle-7.3 gradle

# Add Gradle to PATH
ENV PATH="/opt/app/gradle/bin:${PATH}"

COPY . .

RUN gradle build

FROM eclipse-temurin:17-jre-ubi9-minimal

WORKDIR /opt/app

COPY --from=builder /opt/app/build/libs/covid-api-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]