FROM openjdk:11
ADD /target/*.jar product-service.jar
ENTRYPOINT ["java", "-jar", "product-service.jar"]
EXPOSE 9002