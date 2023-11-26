__________________________________________
                CONTAINER

-The Gadle build works well
|gradle build

-Docker file created
 docker image chosen : eclipse-temurin:17-jre-ubi9-minimal

-tagging the image
 image tag : docker tag covid-api jet00000/covid-api:0.0.8

___________________________________________
JENKINS
-admin user :
    name : administrator
    password : admin
    full name : admin admin
    email : email@email.com

-suggested addons

-docker creds id for indentified push : docker-hub-credentials-id

jenkins build works so everything work well




README section : 

commandes pour créer l'image :
buid gradle : gradle build (plus besoin)
se log a docker : docker login
build docker : docker build -t covid-api .
tag docker image : docker tag covid-api jet00000/covid-api:0.0.1
push the image : docker push jet00000/covid-api:0.0.1
run the image : docker run -p 8080:8080 jet00000/covid-api:0.0.1

l'app cherche la base postgre : conteneur hébergeant la base et l'utiliser avec l'app
-> création d'un docker-compose file
lancer le run : docker-compose up

les tests ne passent plus, je les vire

manip du docker file pour d'abbord build l'app avec la jdk puis envoyer le résultat vers une image jre pour la run

passer à jenkins -> fonctionnel