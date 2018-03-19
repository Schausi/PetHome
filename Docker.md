#Daniel Schausberger - Docker Compose
Docker Compose enables the developer to build and run multiple Docker containers
that may even be dependent on each other.
To start things off you need to have your projects ready and executable.

If you are all set, you have to create the Dockerfiles for your containes in my case it was the db and the jee server.

#####The Dockerfile for the Database
```
FROM airhacks/java
MAINTAINER Adam Bien, adam-bien.com
ENV INSTALL_DIR /opt
ENV VERSION db-derby-10.12.1.1
RUN curl -O http://archive.apache.org/dist/db/derby/db-derby-10.12.1.1/${VERSION}-bin.zip
RUN unzip ${VERSION}-bin.zip -d ${INSTALL_DIR}
ENTRYPOINT ${INSTALL_DIR}/${VERSION}-bin/bin/startNetworkServer -h 0.0.0.0
EXPOSE 1527
```

#####The Dockerfile for the Wildfly
```
FROM jboss/wildfly:latest
RUN /opt/jboss/wildfly/bin/add-user.sh admin passme --silent
ADD customization /opt/jboss/wildfly/customization/
COPY target/PetHome.war /opt/jboss/wildfly/customization/PetHome.war
CMD ["/opt/jboss/wildfly/customization/execute.sh"]
```

You also will need a config file for the wildfly to deploy the war file on the dockered server.

#####The Execute.sh(for derbydb with wildfly)
```
#!/bin/bash

# Usage: execute.sh [WildFly mode] [configuration file]
#
# The default mode is 'standalone' and default configuration is based on the
# mode. It can be 'standalone.xml' or 'domain.xml'.

echo "=> Executing Customization script"

JBOSS_HOME=/opt/jboss/wildfly
JBOSS_CLI=$JBOSS_HOME/bin/jboss-cli.sh
JBOSS_MODE=${1:-"standalone"}
JBOSS_CONFIG=${2:-"$JBOSS_MODE.xml"}

echo "Connection URL:" $CONNECTION_URL

echo "=> Hosts"
cat /etc/hosts
echo
echo "=> Umgebungsvariablen (env)"
env

function wait_for_server() {
  until `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null | grep -q running`; do
    sleep 1
  done
}

echo "=> Starting WildFly server"
$JBOSS_HOME/bin/$JBOSS_MODE.sh -b 0.0.0.0 -c $JBOSS_CONFIG > /dev/null &
# $JBOSS_HOME/bin/$JBOSS_MODE.sh -b 0.0.0.0 -c $JBOSS_CONFIG &

echo "=> Waiting for the server to boot"
wait_for_server


$JBOSS_CLI -c << EOF
batch

module add \
 --name=org.apache.derby \
 --resources=/opt/jboss/wildfly/customization/derby.jar,/opt/jboss/wildfly/customization/derbynet.jar,/opt/jboss/wildfly/customization/derbytools.jar \
 --resource-delimiter=, \
 --dependencies=javax.api,javax.transaction.api

/subsystem=datasources/jdbc-driver=derby:add(driver-name=derby, \
 driver-module-name=org.apache.derby, \
 driver-class-name=org.apache.derby.jdbc.EmbeddedDriver, \
 driver-datasource-class-name=org.apache.derby.jdbc.EmbeddedDataSource, \
 driver-xa-datasource-class-name=org.apache.derby.jdbc.EmbeddedXADataSource)

/system-property=derby.drda.startNetworkServer:add(value=true)

/system-property=derby.drda.portNumber:add(value="1527")


/system-property=derby.infolog.append:add(value="true")

data-source add \
 --name=dbDS \
 --driver-name=derby \
 --connection-url=jdbc:derby:lcoalhost:1527/db;create=true \
 --jndi-name="java:jboss/datasources/ExampleDS" \
 --user-name=app \
 --password=app \
 --enabled=true \
 --use-ccm=false \
 --max-pool-size=25


# Execute the batch
run-batch
EOF

# Deploy the WAR
echo "=> Deploy the WAR"
cp /opt/jboss/wildfly/customization/PetHome.war $JBOSS_HOME/$JBOSS_MODE/deployments/PetHome.war

echo "=> Shutting down WildFly"
if [ "$JBOSS_MODE" = "standalone" ]; then
  $JBOSS_CLI -c ":shutdown"
else
  $JBOSS_CLI -c "/host=*:shutdown"
fi

echo "=> Restarting WildFly"
# $JBOSS_HOME/bin/$JBOSS_MODE.sh -b 0.0.0.0 -bmanagement 0.0.0.0 -c $JBOSS_CONFIG > /dev/null
$JBOSS_HOME/bin/$JBOSS_MODE.sh -b 0.0.0.0 -bmanagement 0.0.0.0 -c $JBOSS_CONFIG
```
#####Last but not least u will need the yaml file for docker-compose
```
version: '3'
services:
  myappserver:
    build: .
    ports:
      - 8080:8080
    depends_on:
      - myappderby
  myappderby:
    build: ./db/.
    ports:
      - 1527:1527
```
####Now you can run docker-compose up in your shell if you are in the project drirectory. Docker will build 2 Containers for your project and will host them for you.
