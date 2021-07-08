FROM openjdk:11
ADD /target/*.jar aws-crud.jar
ENTRYPOINT ["java", "-jar", "aws-crud.jar"]
EXPOSE 8080