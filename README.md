# Hyperion

Projet réalisé dans le cadre du cours **IG338 - Développement avancé d'application Web** à la Henallux - Département technique IESN.

Hyperion est une application Web de vente en ligne sécurisée développée avec Spring Boot. Elle propose un catalogue dynamique, un panier, l'inscription et l'authentification des clients, la prise de commande, un paiement en ligne, des promotions et une interface disponible en français et en anglais.

## Technologies

- Java 26
- Spring Boot 4.1.0
- Spring MVC
- Spring Security
- Spring Data JPA
- Hibernate
- Apache Maven
- Apache Tomcat embarqué
- JSP / JSTL / HTML / CSS
- PostgreSQL
- Jakarta Bean Validation
- JUnit
- Mockito
- PayPal Sandbox

## Fonctionnalités principales

- Consultation du catalogue sans authentification.
- Consultation des catégories, des produits d'une catégorie et du détail d'un produit.
- Ajout d'un produit au panier avec choix de la quantité.
- Consultation, modification et suppression des articles du panier.
- Inscription d'un client avec validation des données saisies.
- Authentification et déconnexion avec Spring Security.
- Modification du compte utilisateur.
- Confirmation et enregistrement d'une commande avant paiement.
- Paiement en ligne via PayPal Sandbox.
- Mise à jour du statut de la commande après validation du paiement.
- Promotions calculées dans la couche métier.
- Interface disponible en français et en anglais.
- Traduction des libellés statiques, de certains libellés dynamiques issus de la base de données et des messages d'erreur.
- Affichage d'un message personnalisé pour l'utilisateur authentifié.

## Prérequis

Installer les outils suivants :

- JDK 26 ;
- Apache Maven ;
- PostgreSQL.

Vérification :

```bash
java -version
mvn -version
psql --version
```

## Configuration de PostgreSQL

La configuration actuelle utilise :

```text
Base de données : hyperion
Hôte : localhost
Port : 5432
Utilisateur : postgres
Mot de passe : mdp
```

Créer la base de données si nécessaire :

```bash
sudo -u postgres psql
```

Puis :

```sql
CREATE DATABASE hyperion;
ALTER USER postgres WITH PASSWORD 'mdp';
```

La configuration correspondante se trouve dans :

```text
src/main/resources/application.yaml
```

avec :

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/hyperion
    username: postgres
    password: mdp
```

Le schéma est créé ou mis à jour par Hibernate grâce à :

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update
```

Le fichier suivant contient les données initiales nécessaires à la démonstration :

```text
src/main/resources/data.sql
```

Il est exécuté automatiquement au démarrage de l'application.

## Construction

Depuis la racine du projet :

```bash
mvn clean install
```

Cette commande compile l'application et exécute les tests Maven.

Pour exécuter uniquement les tests :

```bash
mvn test
```

## Lancement

### Maven

```bash
mvn spring-boot:run
```

### IntelliJ IDEA Ultimate

1. Ouvrir le projet à partir de `pom.xml`.
2. Configurer le SDK du projet sur Java 26.
3. Vérifier que PostgreSQL est démarré.
4. Exécuter la classe `com.hyperion.HyperionApplication`.

L'application est accessible à l'adresse :

```text
http://localhost:8080/
```

## Comptes de démonstration

### Client

```text
Login : user
Mot de passe : mdp
```

### Administrateur

```text
Login : admin
Mot de passe : mdp
```

Les mots de passe enregistrés dans PostgreSQL sont hashés.

## Internationalisation

L'application prend en charge le français et l'anglais.

Les principaux fichiers de traduction sont :

```text
src/main/resources/i18n/messages_fr.properties
src/main/resources/i18n/messages_en.properties
src/main/resources/ValidationMessages_fr.properties
src/main/resources/ValidationMessages_en.properties
```

Les catégories stockées en base utilisent des clés de traduction afin que leurs libellés dynamiques puissent être affichés dans la langue sélectionnée.

## Paiement

Le paiement en ligne utilise PayPal Sandbox.

La configuration PayPal se trouve dans :

```text
src/main/resources/application.yaml
```

La commande est enregistrée en base de données avant le paiement. Une fois le paiement validé, son statut est mis à jour.

## Architecture

L'application suit une architecture MVC et une séparation en couches de type 3-tiers :

```text
Controller -> Service -> Repository -> PostgreSQL
                |
                v
              Model
```

Les principales parties du projet sont organisées comme suit :

```text
src/
├── main/
│   ├── java/com/hyperion/
│   │   ├── configuration/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── service/
│   │   └── session/
│   ├── resources/
│   │   ├── i18n/
│   │   ├── static/
│   │   ├── application.yaml
│   │   └── data.sql
│   └── webapp/WEB-INF/jsp/
└── test/
```

## Remise

Conformément au cahier des charges, le dépôt GitLab de remise doit contenir au minimum :

- le code source complet de l'application ;
- le script de création et, le cas échéant, de population de la base de données ;
- le fichier `pom.xml` nécessaire à la construction avec Maven ;
- toutes les ressources nécessaires au fonctionnement de l'application.
