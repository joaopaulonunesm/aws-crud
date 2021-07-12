FROM openjdk:11
ADD /target/*.jar employee-service.jar
ENTRYPOINT ["java", "-jar", "employee-service.jar"]
EXPOSE 9001