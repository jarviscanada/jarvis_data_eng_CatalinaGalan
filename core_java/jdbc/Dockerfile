FROM openjdk:11

WORKDIR /usr/local/app/jdbc

COPY target/jdbc*.jar lib/jdbc.jar
COPY scripts/stock_quote_docker.sh bin/stock_quote_docker.sh
COPY sql/stockquote/ sql/stockquote
COPY src/main/resources/ src/main/resources

# RUN chmod +x bin/stock_quote_docker.sh

ENTRYPOINT ["java", "-jar", "lib/jdbc.jar"]