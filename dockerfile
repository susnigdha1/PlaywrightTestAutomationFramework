# syntax=docker/dockerfile:1
FROM mcr.microsoft.com/playwright/java:v1.35.0-jammy as playwright
WORKDIR /playwrightTests
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src

FROM playwright as test
CMD ["./mvnw", "verify"]
