FROM postgres:9.6-alpine
USER root
RUN docker run --jrvs-trading -e POSTGRES_PASSWORD=$PGPASSWORD -d  \
    -v pgdata:/var/lib/postgresql.pgdata -p 5432:5432 \
USER $PGUSERNAME
ENTRYPOINT ["entrypoint.bash"]
