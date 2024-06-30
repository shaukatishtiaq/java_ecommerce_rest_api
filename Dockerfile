FROM openjdk:21

COPY target/BackendForEcommerce-0.0.1-SNAPSHOT.jar live.jar

ENTRYPOINT ["java","-jar","live.jar"]
