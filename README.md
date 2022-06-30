Build GitHub :

[![Build with GitHub](https://github.com/republique-et-canton-de-geneve/referentiels-ofs/actions/workflows/maven.yml/badge.svg)](https://github.com/republique-et-canton-de-geneve/referentiels-ofs/actions/workflows/maven.yml)

Analyse SonarCloud :

[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=republique-et-canton-de-geneve_referentiels-ofs&metric=bugs)](https://sonarcloud.io/dashboard?id=republique-et-canton-de-geneve_referentiels-ofs)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=republique-et-canton-de-geneve_referentiels-ofs&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=republique-et-canton-de-geneve_referentiels-ofs)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=republique-et-canton-de-geneve_referentiels-ofs&metric=code_smells)](https://sonarcloud.io/dashboard?id=republique-et-canton-de-geneve_referentiels-ofs)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=republique-et-canton-de-geneve_referentiels-ofs&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=republique-et-canton-de-geneve_referentiels-ofs)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=republique-et-canton-de-geneve_referentiels-ofs&metric=coverage)](https://sonarcloud.io/dashboard?id=republique-et-canton-de-geneve_referentiels-ofs)

Licence :

[![License: AGPL v3](https://img.shields.io/badge/License-AGPL%20v3-blue.svg)](https://www.gnu.org/licenses/why-affero-gpl.html)

# AVERTISSEMENT

Migration du groupId `org.sdmxsource` vers le groupId `com.epam.deltix`
tentée, mais pas achevée, voir la branche `migration-vers-com-epam-deltix`.
En version open source sur GitHub, cette migration est désormais nécessaire car les dépendances Maven
sur le groupId `org.sdmxsource` ne sont plus disponibles, le dépôt Maven de SdmxSource n'étant 
plus accessible et les JAR n'étant pas dans Maven Central.

Pour en savoir plus, voir le début de ce fichier `README.md` sur la branche `migration-vers-com-epam-deltix`.


# Présentation

## Introduction

En Suisse, l'Office fédéral de la statistique (OFS) est un service national mettant à disposition des données statistiques
publiques.
Parmi les données publiées, il met à disposition des référentiels (ou nomenclatures) utilisés pour la constitution
des fichiers de statistiques, permettant ainsi de créer une base référentielle des données statistiques.

## Référentiels et nomenclatures

Pour ses besoins de publication de statistiques, l'OFS a défini un certain nombre de référentiels communs à tous les
cantons suisses.
Ces référentiels sont globaux à la Suisse et sont maintenus à jour par l'OFS.
Ils sont décrits sur le portail de la statistique suisse à l'URL suivante :
[Nomenclatures](https://www.bfs.admin.ch/bfs/fr/home/registres/registre-personnes/harmonisation-registres/nomenclatures.html).
Ils sont publiés au format SDMX qui est un format XML avec un schéma XML dédié à la publication de données statistiques.

Les principaux référentiels intéressant les services informatiques de l'État de Genève sont :
- référentiel des cantons, districts et communes
- référentiel des état civils
- référentiel des formes juridiques
- référentiel des professions
- référentiel des catégories socio-professionnelles
- référentiel des pays

Bien que l'OFS publie d'autres données statistiques (dynamiques), le composant actuel ne s'applique à exposer que
les données des référentiels (relativement statiques).

## Services Web

L'objectif de ce composant est d'exposer les référentiels de l'OFS sous forme de services Web accessibles à
toutes les applications utilisant ces référentiels. 
Ces services Web ont pour principal objectif qu'un maximum de service de l'État reposent sur les mêmes nomenclatures, 
maintenues à jour par un office centralisé.

L'OFS ne proposant pas de services Web permettant d'exploiter ses nomenclatures, ce composant se charge de cette
publication au format SOAP.

Pour des raisons de disponibilité des fichiers SDMX sur le site de l'OFS, il n'est techniquement pas possible que le
composant exploite directement les fichiers SDMX publiés sur le site de l'OFS. De plus, l'URL de la dernière version
de chaque nomenclature évolue, il n'est pas possible d'automatiser la mise à jour des référentiels au fur et à mesure
des publications de l'OFS.
Par conséquent, la mise en oeuvre du composant repose sur une copie locale des fichiers SDMX devant être maintenue
à jour manuellement.

# Installation

## Choix de la branche
Par défaut, choisir la branche `master` : elle contient les sources à jour et produit un livrable .war
utilisable sur un serveur Tomcat en Java 8.

Pour les besoins de l'État de Genève, une branche temporaire `java6` a été créée : elle produit un livrable .ear
contenant le .war et destiné au déploiement sur un serveur JBoss 5.1 en Java 6.

## Construction

### Construction simple
Pour construire l'application, lancer la commande

``mvn clean install``

### Construction avec déploiement intégré
Ce second mode construit l'application, puis la déploie sur un serveur Tomcat et enfin lance les tests
d'intégration SoapUI sur l'application déployée.

Configuration préalable :
- Installer un serveur Tomcat 8+
- Dans le fichier `tomcat-users.xml`, définir un utilisateur appelé `tomcat` avec au moins le rôle `manager-script` :
``<user username="tomcat" password="mot_de_passe" roles="manager-script"/>``
(choisir un meilleur mot de passe !)
- Dans le fichier `referentiels-ofs-war/pom.xml`, adapter les propriétés commençant par `cargo` 
- Lancer le serveur Tomcat
- Si le serveur Tomcat répond à une autre URL que `http://localhost:8080`, adapter la valeur de la propriété
`soapui.endpoint.base` dans le fichier `referentiel-soapui-tests/pom.xml`

Construction, déploiement et test :
Lancer la commande 

``mvn clean install -Ptomcat``

Si l'on préfère ne pas modifier le fichier `referentiel-soapui-tests/pom.xml`, on peut spécifier l'URL dans la 
ligne de commande :

``mvn clean install -Ptomcat -Dsoapui.endpoint.base=http(s)://<HOTE>:<PORT>/referentiels-ofs``

### Note
Ce projet contient un module `DGSI.SERVICESCOMMUNS.REFERENTIELS.LOT.TPS`. Il définit des éléments propres au mode
de déploiement particulier de l'État de Genève. 
Ils sont sans intérêt pour un lecteur externe à l'État, n'interfèrent pas dans la construction Maven du
livrable WAR et ne sont pas concernés par le déploiement et l'exécution sur un serveur Tomcat.

## Déploiement

La construction Maven génère un fichier WAR dans le répertoire `referentiels-ofs-war/target`.
Ce fichier peut être déployé sur tout serveur JEE, tel Tomcat ou JBoss.
