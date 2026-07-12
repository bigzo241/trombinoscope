# Presentation
Trombinoscope est un projet personnel que j'ai developpé dans le but de maitriser le framework JavaFX. \
L'application est sensé juste affichée les informations des membres d'une organisation qui se trouve dans une base de données.

## Fonctionnalites de l'application 
1. Ajouter un individu
2. Modifier les informations d'un individu
3. Supprimer un individu
4. Rechercher un individu à partir de son nom, prenom, email, etc.

## Technologies utilisées
- Langage de programmation : Java 21 (JDK 21)
- Bibliothèque d'interface graphique : JavaFX 21
- Base de données : PostgresSQL 16.1
- IDE : Intellij Idea
- Outil de build : Maven 3.9.11

## Installation et execution
1. Installez le JDK 21+
2. Installez OpenJfx 21 (JavaFX 21)
3. Installez et configurez la base de données PostgreSQL 16 \
     Creer une base de données avec le nom "trombinoscope"\
     Créez la table individu avec la commande SQL suivante : \
       create table individu (\
          nom varchar(50) not null,\
          prenom varchar(100) not null,\
          date datetime not null,\
          poste varchar(20) not null,\
          email varchar(250) not null,\
          numero_tel integer not null,\
          image_path varchar(250) not null,\
          constraint pk_constraint PRIMARY KEY (email))
4. Installez maven 3.9.11
5. Clonez le projet et ouvrez le avec votre éditeur de code préféré
6. Ouvrez la classe COnnectionPostgreSQL.java qui se trouve dans le package dataAccess
7. Modifiez la valeur de la propriété passwd en mettant votre mot de passe administrateur de la base données PostgreSQL
8. Allez y à la racine du projet et tapez la commande suivante :
   mvn clean javafx:run

## Déploiement 
Nous allons créer un installateur native pour windows 10 ou 11
1. Téléchargez et installez Wixtoolset à l'adresse suivante : https://wix-toolset.fr.softonic.com/
2. Téléchargez et installez Inno Setup : https://jrsoftware.org/isdl.php/Inno-Setup-Downloads
3. Ouvrez un terminal ou une invite de commande, allez à la racine du projet et tapez la commande suivante : mvn package
4. Ensuite tapez la commande suivante : mvn dependency:copy-dependencies
5. Créez le fichier d'installation avec la commande suivante :  jpackage --name Trombinoscope --input target --main-jar "trombinoscope-1.0.jar" --main-class com.mamadou.trombinoscope.MainApp --type exe --app-version 1.0 --win-dir-chooser --win-shortcut --win-console --vendor "Access TIC" --module-path "target/dependency" --add-modules javafx.controls,javafx.fxml,java.sql
6. Un fichier Trombinoscope-1.0.exe sera créé à la racine du projet
7. Double cliquez pour installer l'application
