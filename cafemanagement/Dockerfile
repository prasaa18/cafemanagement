# Build stage
FROM maven:3.8.4-openjdk-17 AS BUILD_IMAGE
WORKDIR /usr/src/app/
COPY ./ /usr/src/app/
RUN mvn install -DskipTests

# Final stage
FROM openjdk:17-jdk
WORKDIR /usr/src/app/
COPY --from=BUILD_IMAGE /usr/src/app/target/cafemanagement-v2.jar ./cafemanagement-v2.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "cafemanagement-v2.jar"]
