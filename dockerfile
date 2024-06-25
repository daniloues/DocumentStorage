# Use the official Payara Server Full image as a base
FROM payara/server-full:6.2024.3-jdk17

# Set the user
USER payara

# Copy the application WAR file and other necessary files
COPY ./target /opt/payara/deployments
COPY ./passwordfile /opt/payara/config/passwordfile
COPY ./lib /opt/payara/appserver/glassfish/domains/domain1/lib

# Expose the necessary port
EXPOSE 9090

# Run commands to configure Payara and deploy the application
CMD ["/bin/bash", "-c", "\
    asadmin start-domain && \
    echo 'Running pre-boot commands' && \
    asadmin --user=admin --passwordfile=/opt/payara/config/passwordfile create-jdbc-connection-pool --datasourceclassname org.postgresql.ds.PGSimpleDataSource --restype javax.sql.DataSource --property user=postgres:password=abc123:servername=db:portnumber=5432:databasename=documientostpi135 pgdb && \
    asadmin --user=admin --passwordfile=/opt/payara/config/passwordfile create-jdbc-resource --connectionpoolid pgdb jdbc/pgdb && \
    echo 'Finished running pre-boot commands' && \
    asadmin --user=admin --passwordfile=/opt/payara/config/passwordfile deploy /opt/payara/deployments/Documientos-1.0-SNAPSHOT.war && \
    echo 'WAR file deployed' && \
    asadmin stop-domain && \
    asadmin start-domain --verbose"]

