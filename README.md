# Recipes Api
## Goal: cooking recipes

PWA website to list and search in cooking recipes

### Model:

#### V1
- [x] difficulté: facile ou complexe.. => select
- [x] temps préparation: long ou rapide => slider, step 5
- [ ] temps de cuisson => slider, step 5
- [x] saison: été, hiver => select
- [x] nombre de part => input
- [x] description (peut contenir des ajustements) => text
- [x] "nutriscore" => select
- [x] title => input

#### V2
- [ ] type: plat, entrée, crudité, soupe, quiche, dessert, gâteau.. => hiérarchie + paramétrable
- [ ] note, rating
- [ ] tag:
  - [ ] robot ou non
  - [ ] végétarien ou non, vegan
  - [ ] pour enfant ou pas
  - [ ] testé ou pas
  - [ ] repas de fête, du soir, pour profiter...

#### V3
- [ ] photo: de la recette (scraping ?), du resultat 
- [ ] par ingrédient principal: haricot rouge, pois chiche..

### Details page:

- Recipe: in pdf, jpg, link (marmiton) or text (moulinex) V1
- embeded viewer (V3)
- description: note perso, lien.. V1
- si testé (seulement si admin): popup pour ajouter une note, une photo et modifier la description V2

### Back office:

- se loguer => réutiliser weather V3
- Même app
- créer/modifier une recette V1
- éditer les hiérarchies de type V2
- mobile
- un tenant par user V3
- thème jaune

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

### Roadmap
#### V1 (MVP)
- API:
  - main recipe fields
  - basic search on it
  - creation/edit/deletion
  - Tests
- BO
  - List/search them => which fields on the table ?
  - Details page: no pdf and jpg, only links
  - Creation/Edit form
  - Navigation
  - UX theme
  - Tests
  - No: home page, auth, files, tag, rating
#### V2
- tag: use them on recipes and manage them
- hierarchy: use them on recipes and manage them
- personal rating
#### V3
- authentication for edition (admin)
- store files
- view files
- scraping ingredient, search on them with elastic

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
