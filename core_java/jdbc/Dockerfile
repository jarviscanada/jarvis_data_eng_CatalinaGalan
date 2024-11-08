FROM openjdk:11-jdk-alpine

WORKDIR /usr/local/app/jdbc

RUN mkdir -p lib bin config/sql/stockquote

COPY target/jdbc*.jar lib/jdbc.jar
COPY scripts/psql_docker.sh bin/psql_docker.sh
COPY sql/stockquote/ sql/stockquote

RUN chmod +x bin/psql_docker.sh

ENTRYPOINT ["bin/psql_docker.sh"]