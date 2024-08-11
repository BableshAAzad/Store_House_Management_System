FROM maven:3.9.5-openjdk-21 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
COPY --from=build /target/StoreHouseManagementSystem-0.0.1-SNAPSHOT.jar store-house-management-system.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "store-house-management-system.jar"]
