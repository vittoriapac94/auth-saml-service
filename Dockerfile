FROM openjdk:latest

EXPOSE 8443

ADD target/prova-seria-0.0.1-SNAPSHOT.jar prova-seria-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","prova-seria-0.0.1-SNAPSHOT.jar"]