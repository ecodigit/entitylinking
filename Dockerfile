FROM maven:3.6.3-jdk-8

RUN cd /opt && \
	git clone https://github.com/luigi-asprino/lgu-commons.git && \
	cd /opt/lgu-commons && \
	git checkout 0.0.7.pr  && \
	mvn clean install -Dgpg.skip && \
	cd /opt  && \
	git clone https://github.com/ecodigit/entitylinking.git  && \
	cd /opt/entitylinking  && \
	mvn clean install

EXPOSE 8080

CMD ["/bin/bash", "/opt/entitylinking/start.sh"]