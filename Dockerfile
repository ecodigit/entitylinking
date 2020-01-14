FROM maven:3.6.3-jdk-8

ARG TAGME_KEY

ARG SERVER_PORT

RUN cd /opt && \
	git clone https://github.com/luigi-asprino/rocks-map.git && \
	cd /opt/rocks-map && \
	git checkout 0.0.4.pr && \
	mvn clean install  -Dgpg.skip && \
	cd /opt && \
	git clone https://github.com/luigi-asprino/hdt-java.git && \
	cd /opt/hdt-java && \
	git checkout 2.1.2.pr && \
	mvn clean install && \
	cd /opt && \
	git clone https://github.com/luigi-asprino/lgu-commons.git && \
	cd /opt/lgu-commons && \
	git checkout 0.0.7.pr  && \
	mvn clean install -Dgpg.skip && \
	cd /opt  && \
	git clone https://github.com/luigi-asprino/lgu-commons-nlp.git && \
	cd /opt/lgu-commons-nlp && \
	mvn clean install && \
	cd /opt  && \
	git clone https://github.com/ecodigit/entitylinking.git  && \
	cd /opt/entitylinking  && \
	mvn clean install 
	
RUN echo "port=${SERVER_PORT}" > /opt/entitylinking/config.properties

RUN echo "${TAGME_KEY}" > /opt/entitylinking/tagme.key

EXPOSE ${SERVER_PORT}

CMD ["/bin/bash", "/opt/entitylinking/start.sh"]