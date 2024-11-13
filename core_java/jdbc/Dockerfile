FROM openjdk:11

WORKDIR /usr/local/app/jdbc

COPY target/jdbc*.jar lib/jdbc.jar
COPY sql/stockquote/ sql/stockquote
COPY src/main/resources/ src/main/resources

ENTRYPOINT ["java", "-jar", "lib/jdbc.jar"]