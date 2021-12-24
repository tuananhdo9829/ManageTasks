FROM openjdk:8
ADD target/myproject-1.0.jar demo-docker.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar", "demo-docker.jar"]
