FROM adoptopenjdk/openjdk11:ubi

EXPOSE 8080

RUN mkdir /opt/app

ADD target/projeto.jar /opt/app

ENTRYPOINT ["java", "-jar", "/opt/app/projeto.jar"]