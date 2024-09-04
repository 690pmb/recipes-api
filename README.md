# Recipes Api
My recipes api

# Getting Started
### Docker Compose support
This project contains a Docker Compose file named `compose.yaml`.
In this file, the following services have been defined:

* elasticsearch: [`docker.elastic.co/elasticsearch/elasticsearch:7.17.10`](https://www.docker.elastic.co/r/elasticsearch)
* mariadb: [`mariadb:latest`](https://hub.docker.com/_/mariadb)

Please review the tags of the used images and set them to the same as you're running in production.

## GraalVM Native Support

This project has been configured to let you generate either a lightweight container or a native executable.
It is also possible to run your tests in a native image.

### Lightweight Container with Cloud Native Buildpacks
If you're already familiar with Spring Boot container images support, this is the easiest way to get started.
Docker should be installed and configured on your machine prior to creating the image.

To create the image, run the following goal:

```
$ ./mvnw spring-boot:build-image -Pnative
```

Then, you can run the app like any other container:

```
$ docker run --rm recipes-api:0.0.1-SNAPSHOT
```

### Executable with Native Build Tools
Use this option if you want to explore more options such as running your tests in a native image.
The GraalVM `native-image` compiler should be installed and configured on your machine.

NOTE: GraalVM 22.3+ is required.

To create the executable, run the following goal:

```
$ ./mvnw native:compile -Pnative
```

Then, you can run the app as follows:
```
$ target/recipes-api
```

You can also run your existing tests suite in a native image.
This is an efficient way to validate the compatibility of your application.

To run your existing tests in a native image, run the following goal:

```
$ ./mvnw test -PnativeTest
```

---
## Recettes de cuisine

Site web pwa pour lister et rechercher dans les recettes de cuisine

### Rechercher des recettes de cuisine par:

- type: plat, entrée, crudité, soupe, quiche, dessert, gâteau.. => hiérarchie + paramétrable
- difficulté: facile ou complexe..
- temps: long ou rapide
- saison: été, hiver
- robot ou non
- végétarien ou non
- nombre de part
- pour enfant ou pas
- testé ou pas => note et photo ?
- par ingrédient principal: haricot rouge, pois chiche..
- "nutriscore"
- repas de fête, du soir, pour profiter... => label, tag
- dans le titre

### Détails:

- recette pdf, jpg, lien (marmiton) ou texte (moulinex) => lecteur embarqué
- description: note perso, lien..
- note si testé
- photo si testé

### Back office:

- se loguer => réutiliser weather
- Même app
- créer/modifier une recette
- éditer les hiérarchies de type
- mobile
- un tenant par user
- thème jaune

### Stack:

- api kotlin 21 & spring boot 3
- angular 17 + Material
- postgresql + elastic
- minio

### API

- Hiérarchie crud
- recette crud
- crud
- recette search
- authentification => uniquement put/post/delete
    - user api indépendante
    - un utilisateur avec une liste d'app
    - crud user
    - crud app
    - link user with app
- stockage des fichiers dans minio, des recettes dans elasticsearh et des user dans postgres

Stocker en bdd les recettes, dans un s3 les documents  
Une api pour le crud et l'authentification  
une app front pour les lister et les administrer