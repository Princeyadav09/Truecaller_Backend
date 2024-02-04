FROM openjdk:20
EXPOSE 8080
ADD target/new_repo new_repo.jar
ENTRYPOINT ["java","-jar","/new_repo.jar"]