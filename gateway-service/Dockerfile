FROM openjdk:11
ADD /target/*.jar gateway-service.jar
ENTRYPOINT ["java", "-jar", "gateway-service.jar"]
EXPOSE 8080