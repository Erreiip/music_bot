FROM postgres:15

COPY create.sql /docker-entrypoint-initdb.d/

EXPOSE 5432

CMD ["postgres"]
