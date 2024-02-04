FROM openjdk:20
COPY target/new_repo.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","new_repo.jar"]