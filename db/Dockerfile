FROM postgres:10.0-alpine
USER postgres
COPY initcatalog.sql /docker-entrypoint-initdb.d/
COPY initusers.sql /docker-entrypoint-initdb.d/
COPY initorders.sql /docker-entrypoint-initdb.d/
COPY initstates.sql /docker-entrypoint-initdb.d/
RUN chmod 0700 /var/lib/postgresql/data &&\
    initdb /var/lib/postgresql/data &&\
    echo "host all  all    0.0.0.0/0  md5" >> /var/lib/postgresql/data/pg_hba.conf &&\
    echo "listen_addresses='*'" >> /var/lib/postgresql/data/postgresql.conf &&\
    pg_ctl start



EXPOSE 5432