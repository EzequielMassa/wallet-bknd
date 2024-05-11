FROM alpine:3.19
ARG version=17.0.11.9.1
COPY /target/wallet-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
