FROM postgres:9.6-alpine

COPY create-multiple-databases.sh /docker-entrypoint-initdb.d/

ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=password
ENV POSTGRES_MULTIPLE_DATABASES=jrvstrading,jrvstrading_testing
