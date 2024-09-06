# Recipes Api
## Goal: cooking recipes

PWA website to list and search in cooking recipes

### Features:

- [ ] type: plat, entrée, crudité, soupe, quiche, dessert, gâteau.. => hiérarchie + paramétrable
- [x] difficulté: facile ou complexe..
- [x] temps: long ou rapide
- [x] saison: été, hiver
- [x] nombre de part
- [ ] note, rating
- [ ] photo: de la recette (scraping ?), resultat 
- [x] description (peut contenir des ajustements)
- [ ] par ingrédient principal: haricot rouge, pois chiche..
- [x] "nutriscore"
- [x] dans le titre
- [ ] tag:
  - [ ] robot ou non
  - [ ] végétarien ou non
  - [ ] pour enfant ou pas
  - [ ] testé ou pas
  - [ ] repas de fête, du soir, pour profiter...

### Détails:

- recette pdf, jpg, lien (marmiton) ou texte (moulinex) => lecteur embarqué
- description: note perso, lien..
- si testé (seulement si admin): popup pour ajouter une note, une photo et modifier la description

### Back office:

- se loguer => réutiliser weather
- Même app
- créer/modifier une recette
- éditer les hiérarchies de type
- mobile
- un tenant par user
- thème jaune

### Stack:

- api java 21 & spring boot 3
- angular 19 + Material
- postgresql + elastic
- minio

### Epic Roadmap
- Basic Recipes API:
  - Get
  - Post
  - Put
  - Delete
  - Basic Search
- Hierarchies API:
  - Get
  - Post
  - Put
  - Delete
  - search ?
- Use Hierarchies in recipes to categorize them => reflechir à leur utilisation UX/UI avant
- Adds minio support, for png and pdf
  - reduce size before update it 
  - Result photo
  - Recipe
- Use tags:
  - model
  - CRUD
  - edit recipe crud & search
  - delete orphans
- rating => edit CRUD recipe
- Admin access to edit (put/post/delete)
  - user api indépendante
  - un utilisateur avec une liste d'app
  - crud user
  - crud app
  - link user with app
- scraping ingredients
- search on ingredients
- Adds elastic search support: title, ingredients, description

### Technical Roadmap
- Integration tests
  - with testcontainers
  - with Bruno
- Spring native
- elastic search
- authentication
- minio

## Integration tests
They are written using the tool [Bruno](https://docs.usebruno.com/).  
And [Xunit Viewer](https://github.com/lukejpreston/xunit-viewer) to generate a report.  
They are located in the [bruno folder](./bruno).  
#### Getting started
To install Bruno and Xunit Viewer:
```shell
npm install -g @usebruno/cli
npm i -g xunit-viewer
```
To run tests on the _Local_ environment and generate the html report:
```shell
cd bruno
bru run --env Local --output results.xml -f junit
xunit-viewer -r results.xml -o test-report.html
```
#### Organization
In the _bruno_folder_ you will find:
- _bruno.json_ Bruno configuration file
- _collection_: configuration for the collection
- for each request:
  - bru files: the request to send
  - schema json files: json schema to validate responses
- report folder
- environments folder: configuration for each environment (_Local_ and _Dev_)
