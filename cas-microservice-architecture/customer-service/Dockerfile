FROM java:8-jdk
MAINTAINER Alexandre Arcanjo de Queiroz

RUN apt-get update && \
    apt-get update --fix-missing && \
    apt-get install -y wget

ENV JETTY_VERSION 9.3.14
ENV RELEASE_DATE v20161028
ENV JETTY_HOME /opt/jetty

RUN wget http://central.maven.org/maven2/org/eclipse/jetty/jetty-distribution/${JETTY_VERSION}.${RELEASE_DATE}/jetty-distribution-${JETTY_VERSION}.${RELEASE_DATE}.tar.gz && \
    tar -xzvf jetty-distribution-${JETTY_VERSION}.${RELEASE_DATE}.tar.gz && \
    rm -rf jetty-distribution-${JETTY_VERSION}.${RELEASE_DATE}.tar.gz && \
    mv jetty-distribution-${JETTY_VERSION}.${RELEASE_DATE}/ /opt/jetty

WORKDIR /opt/jetty

ARG KEYSTORE
ARG KEYSTORE_PASSWORD

ADD $KEYSTORE $JETTY_HOME/etc/mycert

RUN keytool -importkeystore -srckeystore $JETTY_HOME/etc/mycert \
            -srcstorepass $KEYSTORE_PASSWORD \
            -destkeystore $JAVA_HOME/jre/lib/security/cacerts \
            -deststorepass $KEYSTORE_PASSWORD -noprompt && \
    java -jar $JETTY_HOME/start.jar --add-to-startd=https,ssl && \
    echo "--exec"  >> $JETTY_HOME/start.ini && \
    echo "-Xdebug" >> $JETTY_HOME/start.ini && \
    echo "-agentlib:jdwp=transport=dt_socket,address=9999,server=y,suspend=n" >> $JETTY_HOME/start.ini && \
    rm $JETTY_HOME/etc/mycert

ADD ./web/target/customer-service-web.war /opt/jetty/webapps/customer-service.war

RUN useradd jetty && \
    chown -R jetty:jetty /opt/jetty && \
    rm -rf demo-base

CMD ["java", "-jar", "start.jar", "jetty.home=/opt/jetty"]
