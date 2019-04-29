L'Office f�d�ral de la statistique (OFS) est un service f�d�ral mettant � disposition des donn�es statistiques
publiques.
Parmi les donn�es publi�es, il met � disposition des r�f�rentiels (ou nomenclatures) utilis�s pour la constitution
des fichiers de statistiques, permettant ainsi de cr�er une base r�f�rentielle des donn�es statistiques.

# Pr�sentation

## R�f�rentiels et nomenclatures

Pour ses besoins de publication de statistiques, l'OFS a d�fini un certain nombre de r�f�rentiels communs � tous les
cantons suisses.
Ces r�f�rentiels sont globaux � la Suisse et sont maintenus � jour par l'OFS.
Ils sont d�crits sur le portail de la statistique Suisse � l'URL suivante :
[Nomenclatures](https://www.bfs.admin.ch/bfs/fr/home/registres/registre-personnes/harmonisation-registres/nomenclatures.html).
Ils sont publi�s au format SDMX qui est un format XML avec un sch�ma XML d�di� � la publication de donn�es statistiques.

Les principaux r�f�rentiels int�ressant les services informatiques de l'�tat de Gen�ve sont :
- r�f�rentiel des cantons, districts et communes
- r�f�rentiel des �tat civils
- r�f�rentiel des formes juridiques
- r�f�rentiel des professions
- r�f�rentiel des cat�gories socio-professionnelles
- r�f�rentiel des pays

Bien que l'OFS publie d'autres donn�es statistiques (dynamiques), le composant actuel ne s'applique � exposer que
les donn�es des r�f�rentiels (relativement statiques).

## Services Web

L'objectif de ce composant est d'exposer les r�f�rentiels de l'OFS sous forme de services Web accessibles �
toutes les applications utilisant ces r�f�rentiels. 
Ces services Web ont pour principal objectif qu'un maximum de service de l'�tat reposent sur les m�mes nomenclatures, 
maintenues � jour par un office centralis�.

L'OFS ne proposant pas de services Web permettant d'exploiter ses nomenclatures, ce composant se charge de cette
publication au format SOAP.

Pour des raisons de disponibilit� des fichiers SDMX sur le site de l'OFS, il n'est techniquement pas possible que le
composant exploite directement les fichiers SDMX publi�s sur le site de l'OFS. De plus, l'URL de la derni�re version
de chaque nomenclature �volue, il n'est pas possible d'automatiser la mise � jour des r�f�rentiels au fur et � mesure
des publications de l'OFS.
Par cons�quent, la mise en oeuvre du composant repose sur une copie locale des fichiers SDMX devant �tre maintenue
� jour manuellement.

# Installation

## Choix de la branche
Par d�faut, choisir la branche `master` : elle contient les sources � jour et produit un livrable .war
utilisable sur un serveur Tomcat en Java 8.

Pour les besoins de l'�tat de Gen�ve, une branche temporaire `java6` a �t� cr��e : elle produit un livrable .ear
contenant le .war, destin� au d�ploiement sur un serveur JBoss 5.1 en Java 6.

## Configuration
La construction compl�te du WAR, comprenant les tests d'int�gration SoapUI, requiert l'installation d'un serveur Tomcat.
Celui-ci sera sollicit� en deux endroits :
- Fichier ``eferentiels-ofs-war/pom.xml`` : plugin Cargo pour d�ployer le WAR sur le serveur Tomcat
- Fichiers SoapUI (voir ci-dessous) : tests des services Web d�ploy�s sur le serveur Tomcat

Proc�dure :
- Installer un serveur Tomcat 8+
- Dans le fichier `tomcat-users.xml`, d�finir un utilisateur appel� `tomcat` avec au moins le r�le `manager-script` :

``<user username="tomcat" password="mot_de_passe" roles="manager-script"/>``
(choisir un meilleur mot de passe !)
- Dans le r�pertoire `referentiel-soapui-tests/src/test/soapui`, dans chaque fichier
  `Referentiel-XXX-soapui-project.xml`
  remplacer les URL `http://tomlab20-01.ceti.etat-ge.ch:20100/...` par celle du serveur Tomcat,
  par exemple `http://localhost:8080/...`. Ceci peut �tre fait soit au moyen d'un �diteur de texte, soit dans SoapUI.

## Construction
Lancer la commande

``mvn clean install -Dtomcat_password=mot_de_passe``

Ce projet contient un module `DGSI.SERVICESCOMMUNS.REFERENTIELS.LOT.TPS`. Il d�finit des �l�ments propres au mode
de d�ploiement particulier de l'�tat de Gen�ve. 
Ils sont sans int�r�t pour un lecteur externe � l'�tat, n'interf�rent pas dans la construction Maven du
livrable WAR et ne sont pas concern�s par le d�ploiement et l'ex�cution sur un serveur Tomcat.

# D�ploiement

La construction Maven g�n�re un fichier WAR dans le r�pertoire `referentiels-ofs-war/target`.
Ce fichier peut �tre d�ploy� sur tout serveur JEE, tel Tomcat ou JBoss.
