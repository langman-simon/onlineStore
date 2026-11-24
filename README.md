# Hyperion

Projet réalisé dans le cadre du cours **TEB3005-NI - Développement avancé d'application Web** à la Henallux - Département technique IESN (Bloc 3, 2026-2027).

Hyperion est une boutique en ligne de répliques et d'équipements **airsoft**, développée avec Spring Boot. Elle propose un catalogue dynamique, un panier, l'inscription et l'authentification des clients, la prise de commande, un paiement en ligne, des promotions et une interface disponible en français et en anglais.

## Technologies

- Java 26
- Spring Boot 4.1
- Spring MVC
- Spring Security
- Spring Data JPA / Hibernate
- Gradle
- Thymeleaf (+ Layout Dialect, Spring Security extras)
- MapStruct
- PostgreSQL (via Docker)
- Jakarta Bean Validation
- JUnit / Mockito
- PayPal Sandbox

## Fonctionnalités principales

- Consultation du catalogue sans authentification, avec recherche, filtre par catégorie et tri.
- Consultation du détail d'un produit.
- Ajout d'un produit au panier avec choix de la quantité.
- Consultation, modification et suppression des articles du panier.
- Inscription d'un client avec validation des données saisies.
- Authentification et déconnexion avec Spring Security.
- Modification du compte utilisateur.
- Confirmation et enregistrement d'une commande avant paiement.
- Paiement en ligne via PayPal Sandbox.
- Promotions calculées dans la couche métier (paliers de fidélité, promotions globales et ciblées).
- Interface d'administration : gestion des produits et des promotions.
- Interface disponible en français et en anglais.

## Prérequis

- JDK 26
- Docker Desktop
- IntelliJ IDEA (recommandé)

Gradle n'a pas besoin d'être installé : le projet utilise le wrapper `./gradlew`.

## Base de données (Docker)

### Option 1 : Docker Compose (recommandé)

Depuis la racine du projet :

```bash
docker compose up -d
```

Le fichier `compose.yaml` crée un conteneur PostgreSQL `hyperion-db` avec les bons identifiants. Les données sont conservées dans un volume Docker entre deux redémarrages.

Pour arrêter la base :

```bash
docker compose down
```

### Option 2 : commande Docker seule

```bash
docker run --name hyperion-db -e POSTGRES_DB=hyperion -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=mdp -p 5432:5432 -d postgres:18
```

Pour relancer le conteneur plus tard :

```bash
docker start hyperion-db
```

### Configuration

La configuration de connexion se trouve dans `src/main/resources/application.yaml` :

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/hyperion
    username: postgres
    password: mdp
```

Le schéma est créé ou mis à jour par Hibernate (`ddl-auto: update`). Le fichier `src/main/resources/data.sql` contient les données de démonstration et est exécuté automatiquement au démarrage.

## Construction

```bash
./gradlew clean build
```

Cette commande compile l'application (dont la génération des mappers MapStruct) et exécute les tests.

Pour exécuter uniquement les tests :

```bash
./gradlew test
```

## Lancement

### Gradle

```bash
./gradlew bootRun
```

### IntelliJ IDEA

1. Ouvrir le dossier du projet (IntelliJ détecte `build.gradle`).
2. Configurer le SDK du projet sur Java 26.
3. Vérifier que le conteneur PostgreSQL est démarré.
4. Lancer la tâche Gradle `bootRun` ou la classe `com.hyperion.HyperionApplication`.

Après un `git pull`, recharger le projet Gradle (icône 🐘) avant de lancer l'application.

L'application est accessible à l'adresse : http://localhost:8080/

## Comptes de démonstration

| Rôle | Login | Mot de passe |
|---|---|---|
| Client | `user` | `mdp` |
| Administrateur | `admin` | `mdp` |

Les mots de passe enregistrés dans PostgreSQL sont hashés avec BCrypt.

## Internationalisation

Les principaux fichiers de traduction sont :

```text
src/main/resources/i18n/messages_fr.properties
src/main/resources/i18n/messages_en.properties
src/main/resources/ValidationMessages_fr.properties
src/main/resources/ValidationMessages_en.properties
```

Les catégories stockées en base utilisent des clés de traduction afin d'être affichées dans la langue sélectionnée. La langue se change avec le paramètre `?lang=fr` ou `?lang=en`.

## Paiement

Le paiement en ligne utilise PayPal Sandbox (configuration dans `application.yaml`). La commande est enregistrée en base avant le paiement, puis son statut est mis à jour une fois le paiement validé.

## Architecture

Architecture MVC en couches :

```text
Controller -> Service -> DAO -> Repository (Spring Data JPA) -> PostgreSQL
                 |          |
               Model  <- MapStruct -> Entity
```

```text
src/
├── main/
│   ├── java/com/hyperion/
│   │   ├── cart/            panier en session
│   │   ├── configuration/   sécurité, i18n, web
│   │   ├── controller/      contrôleurs MVC
│   │   ├── dataAccess/
│   │   │   ├── dao/         accès aux données (interfaces + implémentations)
│   │   │   ├── entity/      entités JPA
│   │   │   ├── repository/  repositories Spring Data
│   │   │   └── util/        mappers MapStruct Entity <-> Model
│   │   ├── dto/             formulaires
│   │   ├── model/           objets métier
│   │   ├── service/         logique métier
│   │   └── session/
│   └── resources/
│       ├── i18n/
│       ├── static/          css, js, images
│       ├── templates/       vues Thymeleaf
│       ├── application.yaml
│       └── data.sql
└── test/
```
