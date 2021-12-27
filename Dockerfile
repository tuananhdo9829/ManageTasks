FROM openjdk:8
ADD target/myproject-1.0.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","app.jar"]
