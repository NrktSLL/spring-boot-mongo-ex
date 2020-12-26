FROM adoptopenjdk/openjdk11:alpine as build
RUN apk add --no-cache maven
WORKDIR /java
COPY . /java
RUN mvn package -Dmaven.test.skip=true
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/java/target/spring-boot-mongo-ex-0.0.1-SNAPSHOT.jar"]