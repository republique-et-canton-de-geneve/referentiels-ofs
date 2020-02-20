################################################
############ Repository Docker
################################################

ARG NEXUS_DOCKER=docker-all.devops.etat-ge.ch

################################################
############ Construction de l'image d'exécution
################################################

FROM ${NEXUS_DOCKER}/distroless/java/jetty:java8
EXPOSE 8080

ADD referentiels-ofs-war/target/referentiels-ofs.war /jetty/webapps/referentiels-ofs.war