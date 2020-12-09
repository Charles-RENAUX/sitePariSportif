Etape 0 : cd docker

Etape 1 : Lancer Docker avec la commande:

docker build -t mpb_tomcat .
docker-compose up -d

Etape 2 : Initialisez la base de donnée avec le fichier mpb.sql puis les tables dans le fichier "jet"

Etape 3 : remplacer l'url dans les 2 javascripts
