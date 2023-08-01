FROM openjdk:latest
COPY ./target/SET08113-coursework-0.1.0.2-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "SET08113-coursework-0.1.0.2-jar-with-dependencies.jar"]