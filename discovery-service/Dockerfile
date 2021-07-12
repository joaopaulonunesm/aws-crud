FROM openjdk:11
ADD /target/*.jar discovery-service.jar
ENTRYPOINT ["java", "-jar", "discovery-service.jar"]
EXPOSE 8761