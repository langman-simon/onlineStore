# Problème répertoriés
- home.jsp cliquer sur une catégorie proposée ramène sur "toutes les catégories" au lieu de la catégorie sélectionnée.
- l'images dans weapon/details.jsp n'affiche rien si absence d'image.
- manque un bouton retourner au catalogue dans weapon/details.jsp.
- user non enregsitré au register quand on tape "Enter" au lieu du bouton créer un compte.
- copier coller l'url d'une commande d'un autre user pour la réutiliser sur un nouvel user renvoie sur une page d'erreur classique, gérer cela.
- refaire la page "mon compte"

# 🚀 Project Hyperion — Complete TODO List
---

# 👤 Users, Roles & Access Control

## 🌐 Anonymous Visitor

- ✅ Permettre à un utilisateur non authentifié d’accéder à la page d’accueil
- ✅ Permettre à un utilisateur non authentifié de consulter la page de présentation de la société
- ✅ Permettre à un utilisateur non authentifié de consulter les catégories
- ✅ Permettre à un utilisateur non authentifié de consulter les produits
- ✅ Permettre à un utilisateur non authentifié de consulter les détails d’un produit
- ✅ Permettre à un utilisateur non authentifié d’ajouter un produit au panier
- ✅ Permettre à un utilisateur non authentifié de choisir une quantité
- ✅ Permettre à un utilisateur non authentifié de consulter son panier
- ✅ Permettre à un utilisateur non authentifié de modifier son panier
- ✅ Permettre à un utilisateur non authentifié de supprimer un produit du panier
- ✅ Permettre à un utilisateur non authentifié de s’inscrire
- ✅ Interdire à un utilisateur non authentifié de confirmer une commande
- ✅ Rediriger l’utilisateur non authentifié vers le login lorsqu’il tente de commander
- ✅ Ramener l’utilisateur vers la page initialement demandée après connexion

## 👤 Authenticated Customer

- ✅ Permettre à un client authentifié d’utiliser toutes les fonctionnalités publiques
- ✅ Permettre à un client authentifié de passer commande
- ✅ Permettre à un client authentifié de payer une commande
- ✅ Afficher un message personnalisé dans la bannière
- ✅ Afficher le login de l’utilisateur connecté
- ✅ Permettre au client de modifier son compte
- ✅ Permettre au client de se déconnecter
- ✅ Faire reprendre au client déconnecté le rôle d’utilisateur anonyme

## 🛡️ Administrator

- ✅ Définir un rôle administrateur
- ✅ Protéger les pages administrateur avec le rôle `ADMIN`
- ✅ Empêcher un utilisateur normal d’accéder aux routes administrateur
- ✅ Permettre à l’administrateur de consulter les catégories
- ✅ Permettre à l’administrateur de consulter les produits
- ✅ Permettre à l’administrateur d’ajouter un produit
- ✅ Permettre à l’administrateur de modifier un produit
- ✅ Permettre à l’administrateur de supprimer un produit
- ✅ Permettre à l’administrateur de gérer les stocks
- [ ] Permettre à l’administrateur de gérer les promotions
- [ ] Permettre à l’administrateur de gérer les catégories si cette fonctionnalité est prévue

---

# 🔐 Registration, Authentication & Account

## 📝 Registration Form

- ✅ Créer une page d’inscription
- ✅ Ajouter un champ nom
- ✅ Ajouter un champ prénom
- ✅ Ajouter un champ adresse de livraison
- ✅ Ajouter un champ adresse email
- ✅ Ajouter un champ téléphone
- ✅ Ajouter un champ login
- ✅ Ajouter un champ mot de passe
- ✅ Ajouter un champ confirmation du mot de passe
- ✅ Ajouter au moins un champ facultatif
- ✅ Identifier clairement les champs obligatoires
- ✅ Afficher les erreurs de validation près des champs concernés
- ✅ Conserver les valeurs valides du formulaire après une erreur
- ✅ Enregistrer le nouvel utilisateur dans PostgreSQL
- ✅ Refuser un login déjà existant (le duplicat n'est pas ajouté en db mais aucun message d'erreur et les champs du form sont effacés)
- ✅ Hasher le mot de passe avant l’enregistrement
- ✅ Ne jamais enregistrer le mot de passe en clair
- ✅ Rediriger vers le login après une inscription réussie
- ✅ Afficher un message de confirmation après inscription

## 🔑 Login

- ✅ Créer une page de connexion
- ✅ Ajouter un champ login
- ✅ Ajouter un champ mot de passe
- [ ] Traiter l’authentification avec Spring Security
- [ ] Charger l’utilisateur depuis PostgreSQL
- [ ] Vérifier le mot de passe hashé avec un `PasswordEncoder`
- [ ] Créer un `UserDetailsService`
- ✅ Attribuer le rôle `USER` aux clients
- ✅ Attribuer le rôle `ADMIN` aux administrateurs
- ✅ Afficher un message en cas d’identifiants incorrects
- ✅ Créer une session authentifiée après connexion
- ✅ Conserver l’utilisateur connecté pendant sa navigation
- ✅ Restaurer la page initialement demandée après connexion

## 🚪 Logout

- ✅ Ajouter un bouton de déconnexion
- ✅ Afficher ce bouton uniquement aux utilisateurs connectés
- [ ] Envoyer la déconnexion en `POST`
- ✅ Ajouter le token CSRF au formulaire de déconnexion
- ✅ Invalider la session après déconnexion
- ✅ Supprimer le contexte de sécurité
- ✅ Rediriger vers l’accueil après déconnexion

## ⚙️ User Account

- ✅ Créer une page de consultation du compte
- ✅ Afficher les informations du client connecté
- ✅ Permettre la modification du nom
- ✅ Permettre la modification du prénom
- ✅ Permettre la modification de l’adresse de livraison
- ✅ Permettre la modification de l’adresse email
- ✅ Permettre la modification du téléphone
- ✅ Permettre la modification des champs facultatifs
- ✅ Valider les données modifiées
- # 🚀 Project Hyperion — Complete TODO List

---

# 👤 Users, Roles & Access Control

## 🌐 Anonymous Visitor

- ✅ Permettre à un utilisateur non authentifié d’accéder à la page d’accueil
- ✅ Permettre à un utilisateur non authentifié de consulter la page de présentation de la société
- ✅ Permettre à un utilisateur non authentifié de consulter les catégories
- ✅ Permettre à un utilisateur non authentifié de consulter les produits
- ✅ Permettre à un utilisateur non authentifié de consulter les détails d’un produit
- ✅ Permettre à un utilisateur non authentifié d’ajouter un produit au panier
- ✅ Permettre à un utilisateur non authentifié de choisir une quantité
- ✅ Permettre à un utilisateur non authentifié de consulter son panier
- ✅ Permettre à un utilisateur non authentifié de modifier son panier
- ✅ Permettre à un utilisateur non authentifié de supprimer un produit du panier
- ✅ Permettre à un utilisateur non authentifié de s’inscrire
- ✅ Interdire à un utilisateur non authentifié de confirmer une commande
- ✅ Rediriger l’utilisateur non authentifié vers le login lorsqu’il tente de commander
- ✅ Ramener l’utilisateur vers la page initialement demandée après connexion

## 👤 Authenticated Customer

- ✅ Permettre à un client authentifié d’utiliser toutes les fonctionnalités publiques
- ✅ Permettre à un client authentifié de passer commande
- [ ] Permettre à un client authentifié de payer une commande
- [ ] Afficher un message personnalisé dans la bannière
- [ ] Afficher le login de l’utilisateur connecté
- ✅ Permettre au client de modifier son compte
- ✅ Permettre au client de se déconnecter
- ✅ Faire reprendre au client déconnecté le rôle d’utilisateur anonyme

## 🛡️ Administrator

- ✅ Définir un rôle administrateur
- ✅ Protéger les pages administrateur avec le rôle `ADMIN`
- ✅ Empêcher un utilisateur normal d’accéder aux routes administrateur
- ✅ Permettre à l’administrateur de consulter les catégories
- ✅ Permettre à l’administrateur de consulter les produits
- ✅ Permettre à l’administrateur d’ajouter un produit
- [ ] Permettre à l’administrateur de modifier un produit
- ✅ Permettre à l’administrateur de supprimer un produit
- [ ] Permettre à l’administrateur de gérer les stocks
- [ ] Permettre à l’administrateur de gérer les promotions
- [ ] Permettre à l’administrateur de gérer les catégories si cette fonctionnalité est prévue

---

# 🔐 Registration, Authentication & Account

## 📝 Registration Form

- ✅ Créer une page d’inscription
- ✅ Ajouter un champ nom
- ✅ Ajouter un champ prénom
- ✅ Ajouter un champ adresse de livraison
- ✅ Ajouter un champ adresse email
- ✅ Ajouter un champ téléphone
- ✅ Ajouter un champ login
- ✅ Ajouter un champ mot de passe
- ✅ Ajouter un champ confirmation du mot de passe
- ✅ Ajouter au moins un champ facultatif
- ✅ Identifier clairement les champs obligatoires
- ✅ Afficher les erreurs de validation près des champs concernés
- ✅ Conserver les valeurs valides du formulaire après une erreur
- ✅ Enregistrer le nouvel utilisateur dans PostgreSQL
- ✅ Refuser un login déjà existant (le duplicat n'est pas ajouté en db mais aucun message d'erreur et les champs du form sont effacés)
- ✅ Hasher le mot de passe avant l’enregistrement
- ✅ Ne jamais enregistrer le mot de passe en clair
- ✅ Rediriger vers le login après une inscription réussie
- ✅ Afficher un message de confirmation après inscription

## 🔑 Login

- ✅ Créer une page de connexion
- ✅ Ajouter un champ login
- ✅ Ajouter un champ mot de passe
- ✅ Traiter l’authentification avec Spring Security
- ✅ Charger l’utilisateur depuis PostgreSQL
- ✅ Vérifier le mot de passe hashé avec un `PasswordEncoder`
- ✅ Créer un `UserDetailsService`
- ✅ Attribuer le rôle `USER` aux clients
- ✅ Attribuer le rôle `ADMIN` aux administrateurs
- ✅ Afficher un message en cas d’identifiants incorrects
- ✅ Créer une session authentifiée après connexion
- ✅ Conserver l’utilisateur connecté pendant sa navigation
- ✅ Restaurer la page initialement demandée après connexion

## 🚪 Logout

- ✅ Ajouter un bouton de déconnexion
- ✅ Afficher ce bouton uniquement aux utilisateurs connectés
- ✅ Envoyer la déconnexion en `POST`
- ✅ Ajouter le token CSRF au formulaire de déconnexion
- ✅ Invalider la session après déconnexion
- ✅ Supprimer le contexte de sécurité
- ✅ Rediriger vers l’accueil après déconnexion

## ⚙️ User Account

- ✅ Créer une page de consultation du compte
- ✅ Afficher les informations du client connecté
- ✅ Permettre la modification du nom
- ✅ Permettre la modification du prénom
- ✅ Permettre la modification de l’adresse de livraison
- ✅ Permettre la modification de l’adresse email
- ✅ Permettre la modification du téléphone
- ✅ Permettre la modification des champs facultatifs
- ✅ Valider les données modifiées 
- ✅ Enregistrer les modifications dans PostgreSQL
- ✅ Afficher un message après modification réussie
- ✅ Empêcher un utilisateur de modifier le compte d’un autre utilisateur

---

# 🏠 Global Website Structure

## 🧩 JSP Template

- ✅ Créer un template JSP général
- ✅ Créer un header commun
- ✅ Créer un footer commun
- ✅ Créer une zone dynamique pour le contenu principal
- ✅ Utiliser le template sur toutes les pages
- ✅ Éviter de dupliquer le header et le footer
- ✅ Centraliser les imports JSTL
- ✅ Centraliser les imports Spring Security JSP
- ✅ Configurer l’encodage UTF-8
- ✅ Utiliser les routes générées avec `<c:url>`

## 🧭 Global Navigation

Chaque page doit proposer au minimum :

- ✅ Un lien vers l’accueil
- ✅ Un lien vers la page de présentation de la société
- ✅ Un lien vers le catalogue
- ✅ Un lien vers le panier
- ✅ Le nombre total d’articles présents dans le panier
- ✅ Un lien vers l’inscription pour un utilisateur anonyme
- ✅ Un lien vers le login pour un utilisateur anonyme
- ✅ Un bouton de déconnexion pour un utilisateur authentifié
- ✅ Un lien vers le compte utilisateur pour un utilisateur authentifié
- ✅ Un choix de langue
- ✅ Un lien vers l’administration pour un administrateur
- ✅ Un message personnalisé pour un utilisateur connecté

---

# 🏠 Home & Company Pages

## 🏠 Home Page

- ✅ Créer une page d’accueil
- ✅ Présenter l’activité de la société
- ✅ Présenter les produits ou catégories principales
- ✅ Ajouter un accès rapide au catalogue
- ✅ Ajouter un accès rapide au panier
- [ ] Traduire le contenu statique de l’accueil

## 🏢 Company Page

- ✅ Créer une page statique « Notre société »
- ✅ Présenter l’entreprise
- ✅ Présenter son activité
- ✅ Présenter ses services
- ✅ Utiliser le template général
- [ ] Traduire la page en français et en anglais

---

# 📦 Catalog & Categories

## 🗂️ Categories

- ✅ Créer l’entité `Category`
- ✅ Ajouter un identifiant unique
- ✅ Ajouter un nom
- ✅ Ajouter une description
- ✅ Enregistrer les catégories dans PostgreSQL
- ✅ Créer `CategoryRepository`
- ✅ Créer une couche service pour les catégories si nécessaire
- ✅ Afficher dynamiquement les catégories depuis la base de données
- ✅ Ne pas coder les catégories directement dans les JSP
- ✅ Répercuter automatiquement les modifications de la base sur le site
- ✅ Proposer plusieurs catégories
- ✅ Prévoir plusieurs produits dans chaque catégorie
- [ ] Traduire dynamiquement les noms des catégories
- [ ] Traduire dynamiquement les descriptions des catégories

## 🔎 Category Selection

- ✅ Permettre à l’utilisateur de choisir une catégorie
- ✅ Charger les produits correspondant à la catégorie
- ✅ Afficher uniquement les produits de la catégorie choisie
- ✅ Prévoir une option pour afficher toutes les catégories
- [ ] Gérer une catégorie inexistante
- [ ] Gérer une catégorie sans produit
- [ ] Afficher un message traduit lorsqu’aucun produit n’est disponible

## 📦 Products

- ✅ Créer l’entité `Weapon`
- ✅ Ajouter un identifiant unique
- ✅ Ajouter un libellé (c'est le name)
- ✅ Ajouter une description
- ✅ Ajouter un prix unitaire
- ✅ Ajouter un stock
- ✅ Ajouter une référence unique
- ✅ Ajouter un fabricant
- ✅ Ajouter une catégorie
- ✅ Ajouter une image
- ✅ Enregistrer les produits dans PostgreSQL
- ✅ Créer `WeaponRepository`
- ✅ Afficher dynamiquement les produits depuis la base de données
- ✅ Répercuter automatiquement les modifications de la base sur le site
- ✅ Afficher au minimum le libellé dans la liste
- ✅ Afficher au minimum le prix unitaire dans la liste
- ✅ Ajouter un lien vers le détail du produit
- ✅ Afficher l’état du stock
- ✅ Signaler les produits en rupture de stock
- ✅ Empêcher l’ajout au panier d’un produit sans stock
- [ ] Trier les produits de façon cohérente
- [ ] Ajouter une recherche par nom
- ✅ Ajouter un filtre par catégorie
- [ ] Ajouter éventuellement d’autres filtres utiles

---

# 🔍 Product Details

- ✅ Créer une page de détail produit
- ✅ Afficher le nom du produit
- ✅ Afficher la description
- ✅ Afficher le prix
- ✅ Afficher la catégorie
- ✅ Afficher le fabricant
- ✅ Afficher la référence
- ✅ Afficher le stock
- ✅ Afficher l’image
- ✅ Gérer l’absence d’image
- ✅ Ajouter un champ quantité
- ✅ Définir une quantité minimale de 1
- ✅ Limiter la quantité au stock disponible
- ✅ Ajouter un bouton « Ajouter au panier »
- ✅ Ajouter un token CSRF au formulaire `POST`
- ✅ Refuser une quantité négative
- ✅ Refuser une quantité nulle
- ✅ Refuser une quantité supérieure au stock
- ✅ Afficher un message après ajout au panier
- [ ] Afficher un message d’erreur traduit en cas d’échec
- [ ] Gérer un identifiant produit inexistant

---

# 🛒 Shopping Cart

## 🧠 Cart Model

- ✅ Créer une classe `Cart`
- ✅ Créer une classe `CartItem`
- ✅ Stocker le produit dans chaque ligne
- ✅ Stocker la quantité dans chaque ligne
- ✅ Calculer le sous-total de chaque ligne
- ✅ Calculer le nombre total d’articles
- ✅ Calculer le montant total du panier
- ✅ Ajouter une méthode d’ajout de produit
- ✅ Ajouter une méthode de modification de quantité
- ✅ Ajouter une méthode de suppression d’article
- ✅ Ajouter une méthode pour vider le panier
- [ ] Ajouter une méthode pour vérifier si le panier est vide

## 🧑‍💻 Session Cart

- [ ] Stocker le panier dans la session HTTP
- [ ] Utiliser un composant `@SessionScope`
- ✅ Conserver le panier pendant toute la navigation
- ✅ Permettre à un utilisateur anonyme de posséder un panier
- ✅ Conserver le panier après authentification
- ✅ Rendre le nombre d’articles visible depuis toutes les pages
- ✅ Ne pas diminuer le stock lors de l’ajout au panier
- [ ] Diminuer le stock uniquement au moment prévu par le processus de commande

## 🛍️ Cart Page

- ✅ Créer une page de consultation du panier
- ✅ Afficher le libellé de chaque produit
- ✅ Afficher le prix unitaire
- ✅ Afficher la quantité
- ✅ Afficher le sous-total
- ✅ Afficher le montant total
- ✅ Ajouter un lien vers le détail du produit
- ✅ Ajouter un formulaire de modification de quantité
- ✅ Ajouter un bouton de suppression
- ✅ Ajouter un bouton pour vider le panier
- ✅ Ajouter un bouton pour continuer vers la commande
- ✅ Ajouter un token CSRF à chaque formulaire `POST`
- ✅ Afficher un message lorsque le panier est vide
- ✅ Empêcher les quantités nulles ou négatives
- ✅ Empêcher les quantités supérieures au stock
- [ ] Afficher des messages de succès
- [ ] Afficher des messages d’erreur traduits

---

# 📋 Checkout & Order Confirmation

## ✅ Checkout Page

- ✅ Créer une page `/checkout`
- ✅ Protéger `/checkout` avec Spring Security
- ✅ Autoriser uniquement les utilisateurs authentifiés
- ✅ Rediriger un utilisateur anonyme vers `/login`
- ✅ Restaurer `/checkout` après une connexion réussie
- ✅ Afficher le récapitulatif du panier
- ✅ Afficher les produits
- ✅ Afficher les quantités
- ✅ Afficher les prix unitaires
- ✅ Afficher les sous-totaux
- ✅ Afficher le montant total
- [ ] Afficher l’adresse de livraison
- [ ] Demander une confirmation avant l’enregistrement
- ✅ Ajouter un bouton « Confirmer la commande »
- ✅ Ajouter un token CSRF au formulaire de confirmation
- ✅ Empêcher la confirmation d’un panier vide

## 🧾 Order Persistence

- ✅ Créer l’entité `CustomerOrder`
- ✅ Créer l’entité `OrderItem`
- ✅ Relier une commande à ses lignes de commande
- ✅ Relier une commande au client authentifié
- ✅ Enregistrer la date de création
- ✅ Enregistrer le montant total
- ✅ Enregistrer le statut de paiement
- ✅ Enregistrer les lignes de commande
- ✅ Enregistrer le produit commandé
- ✅ Enregistrer la quantité commandée
- [ ] Enregistrer le prix appliqué au moment de la commande
- [ ] Enregistrer la commande avant le paiement
- [ ] Enregistrer toutes les lignes avant le paiement
- [ ] Utiliser une transaction pour valider la commande
- [ ] Vérifier le stock avant validation
- [ ] Réduire le stock après confirmation
- [ ] Annuler toute l’opération en cas d’erreur
- [ ] Vider le panier uniquement après une validation réussie
- [ ] Ne pas vider le panier si la commande échoue
- [ ] Rediriger vers la page de détail de la commande
- [ ] Afficher le numéro de commande
- [ ] Afficher toutes les lignes de la commande
- [ ] Afficher le montant total
- [ ] Afficher le statut payé/non payé
- [ ] Empêcher un utilisateur d’accéder à la commande d’un autre client

---

# 💳 Online Payment

## 💰 General Payment Requirements

- [ ] Proposer au moins une technique de paiement en ligne
- [ ] Utiliser PayPal ou une autre solution de paiement
- [ ] Enregistrer la commande avant le début du paiement
- [ ] Ne pas marquer directement la commande comme payée
- [ ] Créer un statut initial non payé
- [ ] Rediriger l’utilisateur vers le service de paiement
- [ ] Fournir le montant correct au service de paiement
- [ ] Fournir un identifiant de commande
- [ ] Fournir une devise
- [ ] Prévoir une URL de retour en cas de succès
- [ ] Prévoir une URL de retour en cas d’annulation
- [ ] Afficher une page après paiement réussi
- [ ] Afficher une page après paiement annulé
- [ ] Marquer la commande comme payée uniquement après validation du paiement
- [ ] Conserver la commande non payée si le paiement est annulé
- [ ] Empêcher le paiement d’une commande inexistante
- [ ] Empêcher le paiement d’une commande appartenant à un autre utilisateur
- [ ] Empêcher de payer deux fois la même commande
- [ ] Vérifier le montant côté serveur
- [ ] Ne pas faire confiance au montant envoyé par le navigateur
- [ ] Journaliser ou enregistrer la référence du paiement
- [ ] Tester le paiement avec un environnement Sandbox

## 🧪 PayPal Sandbox

- [ ] Créer un compte PayPal Developer
- [ ] Créer un compte vendeur Sandbox
- [ ] Créer un compte acheteur Sandbox
- [ ] Créer une application PayPal Sandbox
- [ ] Récupérer le Client ID
- [ ] Récupérer le Secret si nécessaire
- [ ] Ne pas enregistrer les secrets directement dans Git
- [ ] Stocker les identifiants dans la configuration
- [ ] Configurer l’adresse du vendeur Sandbox
- [ ] Envoyer le montant de la commande
- [ ] Envoyer le nom ou la référence de la commande
- [ ] Configurer `currency_code`
- [ ] Configurer l’URL `return`
- [ ] Configurer l’URL `cancel_return`
- [ ] Tester un paiement accepté
- [ ] Tester un paiement annulé
- [ ] Tester une erreur de paiement
- [ ] Vérifier la mise à jour du statut en base de données
- [ ] Vérifier que le panier reste vide après création de la commande
- [ ] Vérifier que la commande reste consultable après paiement

---

# 🏷️ Promotions

- ✅ Proposer au moins un système de promotion
- ✅ Définir les règles de promotion
- [ ] Faire preuve d’originalité dans les promotions
- ✅ Créer un service métier pour calculer les promotions
- ✅ Ne pas calculer les promotions directement dans la JSP
- ✅ Ne pas calculer les promotions directement dans le contrôleur
- ✅ Appliquer les promotions dans la couche business
- ✅ Afficher clairement le prix initial
- ✅ Afficher clairement la réduction
- ✅ Afficher clairement le prix final
- ✅ Intégrer la réduction au calcul du panier
- ✅ Intégrer la réduction au calcul de la commande
- ✅ Enregistrer le montant réellement facturé
- ✅ Empêcher un montant négatif
- ✅ Tester les promotions avec plusieurs cas
- ✅ Tester un panier sans promotion
- ✅ Tester un panier avec promotion
- ✅ Tester les limites des promotions
- [ ] Traduire les messages liés aux promotions

Exemples possibles :

- [ ] Promotion selon le montant total du panier
- [ ] Promotion selon la quantité achetée
- [ ] Promotion sur une catégorie
- [ ] Promotion sur certains produits
- [ ] Promotion temporaire
- [ ] Promotion réservée aux clients authentifiés

---

# 🌍 Internationalisation

## 🗣️ Language Selection

- [ ] Proposer au minimum le français
- [ ] Proposer au minimum l’anglais
- [ ] Ajouter un sélecteur de langue visible depuis toutes les pages
- [ ] Conserver la langue choisie pendant la session
- [ ] Utiliser la langue choisie lors de la navigation
- [ ] Prévoir une langue par défaut
- [ ] Ne pas perdre la langue après connexion
- [ ] Ne pas perdre la langue après déconnexion

## 📝 Static Labels

- [ ] Traduire les menus
- [ ] Traduire les boutons
- [ ] Traduire les titres
- [ ] Traduire les formulaires
- [ ] Traduire les labels
- [ ] Traduire les messages de confirmation
- [ ] Traduire les pages statiques
- [ ] Créer les fichiers de messages français
- [ ] Créer les fichiers de messages anglais

## 🗄️ Dynamic Labels

- [ ] Traduire les noms de catégories provenant de la base de données
- [ ] Traduire les descriptions de catégories
- [ ] Traduire certains noms ou descriptions de produits si nécessaire
- [ ] Concevoir une stratégie de stockage multilingue
- [ ] Charger la bonne traduction selon la langue active
- [ ] Prévoir une valeur de secours lorsqu’une traduction manque

## ⚠️ Error Messages

- [ ] Traduire les erreurs d’inscription
- [ ] Traduire les erreurs de connexion
- [ ] Traduire les erreurs du panier
- [ ] Traduire les erreurs de stock
- [ ] Traduire les erreurs de commande
- [ ] Traduire les erreurs de paiement
- [ ] Traduire les erreurs de validation
- [ ] Éviter les messages d’erreur codés directement en dur

---

# ✅ Data Validation

## 📝 Required Fields

- [ ] Vérifier que les champs obligatoires sont remplis
- [ ] Afficher un message pour chaque champ obligatoire vide
- [ ] Utiliser les annotations Jakarta Validation
- [ ] Utiliser `@Valid` dans les contrôleurs
- [ ] Utiliser `BindingResult`
- [ ] Empêcher l’enregistrement si le formulaire contient des erreurs

## 🔢 Numeric Fields

- [ ] Vérifier que les valeurs numériques contiennent des nombres valides
- [ ] Vérifier que le stock est positif ou nul
- [ ] Vérifier que le prix est positif
- [ ] Vérifier que la quantité commandée est strictement positive
- [ ] Vérifier que la quantité ne dépasse pas le stock
- [ ] Gérer les valeurs trop grandes
- [ ] Gérer les nombres décimaux correctement

## 🔑 Password Validation

- [ ] Ajouter deux champs de mot de passe lors de l’inscription
- [ ] Vérifier que les deux mots de passe sont identiques
- [ ] Définir une longueur minimale
- [ ] Définir une longueur maximale
- [ ] Hasher le mot de passe
- [ ] Ne jamais renvoyer le hash dans une page Web
- [ ] Ne jamais afficher le mot de passe dans les logs

## 📧 Email Validation

- [ ] Vérifier que l’adresse email est obligatoire
- [ ] Vérifier que le format de l’adresse email est valide
- [ ] Vérifier la présence de `@`
- [ ] Vérifier la présence d’un domaine
- [ ] Définir une longueur maximale
- [ ] Empêcher les valeurs vides ou composées uniquement d’espaces

## 📞 Phone & Postal Data

- [ ] Définir une longueur minimale pour le téléphone
- [ ] Définir une longueur maximale pour le téléphone
- [ ] Valider le format du téléphone
- [ ] Valider le code postal si un champ séparé est utilisé
- [ ] Définir une longueur minimale pour le code postal
- [ ] Définir une longueur maximale pour le code postal
- [ ] Valider l’adresse de livraison

## 👤 Login Validation

- [ ] Vérifier que le login n’est pas vide
- [ ] Définir une longueur minimale
- [ ] Définir une longueur maximale
- [ ] Vérifier que le login n’existe pas déjà
- [ ] Créer une contrainte unique en base de données
- [ ] Gérer proprement les conflits de login

---

# 🛡️ Security

## 🔒 Spring Security

- [ ] Configurer `SecurityFilterChain`
- [ ] Autoriser les ressources statiques
- [ ] Autoriser l’accueil
- [ ] Autoriser le catalogue
- [ ] Autoriser le détail des produits
- [ ] Autoriser le panier aux utilisateurs anonymes
- [ ] Autoriser le login
- [ ] Autoriser l’inscription
- [ ] Protéger le checkout
- [ ] Protéger les commandes
- [ ] Protéger l’administration
- [ ] Configurer `formLogin`
- [ ] Configurer `logout`
- [ ] Configurer les redirections après connexion
- [ ] Configurer les redirections après déconnexion
- [ ] Configurer les rôles
- [ ] Utiliser `hasRole("ADMIN")` pour l’administration
- [ ] Utiliser `authenticated()` pour les commandes

## 🛡️ CSRF

- [ ] Conserver la protection CSRF activée
- ✅ Ajouter un token CSRF aux formulaires `POST`
- ✅ Ajouter un token CSRF aux ajouts au panier
- ✅ Ajouter un token CSRF aux modifications de quantité
- ✅ Ajouter un token CSRF aux suppressions
- ✅ Ajouter un token CSRF à l’inscription
- ✅ Ajouter un token CSRF au login si nécessaire
- ✅ Ajouter un token CSRF au logout
- ✅ Ajouter un token CSRF à la confirmation de commande
- ✅ Ajouter un token CSRF aux actions administrateur
- ✅ Ne pas ajouter de token CSRF aux simples liens `GET`

## 💉 Injection Protection

- [ ] Utiliser JPA/Hibernate pour les requêtes
- [ ] Éviter les requêtes SQL construites par concaténation
- [ ] Utiliser des paramètres dans les requêtes
- [ ] Valider les identifiants reçus dans les URL
- [ ] Échapper correctement les données affichées
- [ ] Ne pas afficher de données non contrôlées avec du HTML brut
- [ ] Protéger l’application contre les injections SQL
- [ ] Protéger l’application contre les attaques XSS
- [ ] Ne pas exposer les secrets dans le code source
- [ ] Ne pas exposer les mots de passe dans les logs
- [ ] Ne pas faire confiance aux valeurs envoyées par le navigateur

## 🔐 Password Storage

- [ ] Utiliser un algorithme récent
- [ ] Utiliser BCrypt ou un équivalent reconnu
- [ ] Créer un bean `PasswordEncoder`
- [ ] Vérifier les mots de passe avec `matches`
- [ ] Vérifier que les hashes ne sont pas tronqués en base de données
- [ ] Prévoir une colonne suffisamment longue
- [ ] Vérifier qu’aucun mot de passe en clair n’est présent dans les scripts SQL

---

# 🏗️ Architecture

## 🧱 Three-Tier Architecture

- [ ] Structurer l’application en trois couches
- [ ] Créer une couche présentation
- [ ] Créer une couche métier
- [ ] Créer une couche accès aux données
- [ ] Éviter l’accès direct aux repositories depuis les JSP
- [ ] Éviter la logique métier dans les JSP
- [ ] Éviter les calculs métier dans les contrôleurs
- [ ] Centraliser les règles métier dans les services
- [ ] Utiliser l’injection de dépendances
- [ ] Réduire le couplage entre les couches
- [ ] Utiliser des interfaces lorsque cela apporte une abstraction utile

## 🎮 MVC Architecture

- [ ] Créer les classes Model
- [ ] Créer les contrôleurs Spring MVC
- [ ] Créer les vues JSP
- [ ] Transmettre les données via le `Model`
- [ ] Utiliser les contrôleurs pour gérer les requêtes
- [ ] Utiliser les JSP uniquement pour l’affichage
- [ ] Utiliser le pattern Post/Redirect/Get après les formulaires
- [ ] Utiliser `RedirectAttributes` pour les messages flash

## 📂 Suggested Packages

- [ ] Créer un package `controller`
- [ ] Créer un package `service`
- [ ] Créer un package `repository`
- [ ] Créer un package `model`
- [ ] Créer un package `security`
- [ ] Créer un package `config`
- [ ] Créer un package `session`
- [ ] Créer un package `validation`
- [ ] Créer un package `exception` si nécessaire

---

# 🗄️ Database & Persistence

## 🐘 PostgreSQL

- [ ] Créer une base de données PostgreSQL
- [ ] Configurer la connexion dans `application.yaml`
- [ ] Configurer l’URL JDBC
- [ ] Configurer le nom d’utilisateur
- [ ] Configurer le mot de passe via une méthode sécurisée
- [ ] Configurer Hibernate
- [ ] Configurer la stratégie DDL
- [ ] Vérifier les contraintes de colonnes
- [ ] Vérifier les contraintes d’unicité
- [ ] Vérifier les clés étrangères
- [ ] Vérifier les relations entre entités

## 🧬 Entities

- [ ] Définir les cascades de façon prudente
- [ ] Définir les longueurs maximales
- [ ] Utiliser `BigDecimal` pour les montants

## 🌱 Test Data

- [ ] Ajouter plusieurs catégories
- [ ] Ajouter plusieurs produits par catégorie
- [ ] Ajouter plusieurs utilisateurs
- [ ] Ajouter au moins un utilisateur normal
- [ ] Ajouter au moins un administrateur
- [ ] Ajouter des stocks variés
- [ ] Ajouter des produits en rupture de stock
- [ ] Ajouter des images
- [ ] Ajouter des données permettant de tester les promotions
- [ ] Ajouter des données permettant de tester le paiement
- [ ] Vérifier que les mots de passe de test sont hashés
- [ ] Fournir un script de création
- [ ] Fournir un script de population

---

# 🎨 JSP, HTML & CSS

## 🖼️ User Interface

- [ ] Créer une interface cohérente
- [ ] Utiliser CSS pour toute la mise en forme
- [ ] Éviter le style directement dans les balises HTML
- [ ] Créer une feuille CSS principale
- [ ] Structurer clairement le header
- [ ] Structurer clairement le contenu
- [ ] Structurer clairement le footer
- [ ] Styliser les formulaires
- [ ] Styliser les boutons
- [ ] Styliser les tableaux
- [ ] Styliser les messages de succès
- [ ] Styliser les messages d’erreur
- [ ] Afficher correctement les images
- [ ] Prévoir une interface utilisable sur différentes tailles d’écran
- [ ] Vérifier l’accessibilité minimale des formulaires
- [ ] Associer chaque champ à un `<label>`
- [ ] Ajouter des textes alternatifs aux images

## 📄 Dynamic Pages

- [ ] Rendre la page des catégories dynamique
- [ ] Rendre la liste des produits dynamique
- [ ] Rendre le détail produit dynamique
- [ ] Rendre le panier dynamique
- [ ] Rendre le checkout dynamique
- [ ] Rendre le détail de commande dynamique
- [ ] Ne pas coder les produits directement dans les JSP
- [ ] Ne pas coder les catégories directement dans les JSP

---

# 🧪 Tests

## ✅ Unit Tests

- [ ] Ajouter plusieurs tests unitaires de services
- [ ] Utiliser JUnit
- [ ] Utiliser Mockito
- [ ] Utiliser AssertJ
- [ ] Structurer les tests avec Arrange / Act / Assert
- [ ] Utiliser `@BeforeEach` lorsque nécessaire
- [ ] Mock les repositories
- [ ] Ne pas dépendre de PostgreSQL dans les tests unitaires

## 🛒 Cart Tests

- [ ] Tester l’ajout d’un nouveau produit
- [ ] Tester l’ajout d’un produit déjà présent
- [ ] Tester la modification de quantité
- [ ] Tester la suppression d’un produit
- [ ] Tester le vidage du panier
- [ ] Tester le montant total
- [ ] Tester la quantité totale
- [ ] Tester une quantité invalide
- [ ] Tester un panier vide

## 📦 Order Tests

- [ ] Tester la création d’une commande
- [ ] Tester la création des lignes de commande
- [ ] Tester le montant total
- [ ] Tester la diminution du stock
- [ ] Tester le vidage du panier
- [ ] Tester un panier vide
- [ ] Tester un stock insuffisant
- [ ] Tester un produit inexistant
- [ ] Tester qu’une erreur empêche la validation complète
- [ ] Tester que la transaction est cohérente

## 🏷️ Promotion Tests

- [ ] Tester une promotion applicable
- [ ] Tester une promotion non applicable
- [ ] Tester une promotion sur une limite exacte
- [ ] Tester plusieurs produits
- [ ] Tester un montant final correct
- [ ] Tester qu’un montant ne devient jamais négatif

## 👤 User Tests

- [ ] Tester l’inscription d’un utilisateur valide
- [ ] Tester un login déjà utilisé
- [ ] Tester deux mots de passe différents
- [ ] Tester un email invalide
- [ ] Tester les champs obligatoires
- [ ] Tester le hashage du mot de passe

## 💳 Payment Tests

- [ ] Tester la création d’un paiement
- [ ] Tester le retour de paiement réussi
- [ ] Tester le retour de paiement annulé
- [ ] Tester la mise à jour du statut payé
- [ ] Tester une commande déjà payée
- [ ] Tester une commande inexistante
- [ ] Tester une commande appartenant à un autre utilisateur

---

# ⚙️ Technical Stack

- [ ] Utiliser Java
- [ ] Utiliser Spring Boot
- [ ] Utiliser Apache Maven
- [ ] Utiliser Hibernate
- [ ] Utiliser Apache Tomcat
- [ ] Utiliser JUnit
- [ ] Utiliser Mockito
- [ ] Utiliser JSP
- [ ] Utiliser HTML
- [ ] Utiliser CSS
- [ ] Utiliser PostgreSQL
- [ ] Configurer correctement le `pom.xml`
- [ ] Vérifier que `mvn clean test` fonctionne
- [ ] Vérifier que `mvn clean package` fonctionne
- [ ] Vérifier que l’application démarre sans erreur
- [ ] Vérifier que le fichier WAR peut être construit

---

# 📦 Deliverables

- [ ] Déposer le code source complet sur GitLab
- [ ] Déposer le script de création de la base de données
- [ ] Déposer le script de population de la base de données
- [ ] Déposer le fichier `pom.xml`
- [ ] Déposer les JSP
- [ ] Déposer les fichiers CSS
- [ ] Déposer les images nécessaires
- [ ] Déposer les fichiers de traduction
- [ ] Déposer les fichiers de configuration nécessaires
- [ ] Déposer toutes les ressources nécessaires au fonctionnement
- [ ] Vérifier qu’aucun secret réel n’est présent dans Git
- [ ] Vérifier qu’aucun mot de passe en clair n’est présent dans Git
- [ ] Vérifier que le projet peut être cloné et démarré
- [ ] Ajouter un README avec les instructions d’installation
- [ ] Ajouter les instructions de création de la base
- [ ] Ajouter les comptes de démonstration
- [ ] Ajouter les instructions pour le paiement Sandbox
- [ ] Vérifier la date limite de remise
- [ ] Ne plus modifier le repository après la date limite

---

# 🎤 Oral Defence

- [ ] Préparer une démonstration complète
- [ ] Préparer un parcours utilisateur anonyme
- [ ] Préparer une inscription
- [ ] Préparer une connexion
- [ ] Préparer une démonstration du panier
- [ ] Préparer une démonstration de commande
- [ ] Préparer une démonstration du paiement
- [ ] Préparer une démonstration des promotions
- [ ] Préparer une démonstration multilingue
- [ ] Préparer une démonstration administrateur
- [ ] Préparer une démonstration des validations
- [ ] Préparer une démonstration des protections de sécurité
- [ ] Préparer une présentation de l’architecture MVC
- [ ] Préparer une présentation de l’architecture 3-tiers
- [ ] Être capable d’expliquer Spring Security
- [ ] Être capable d’expliquer les sessions
- [ ] Être capable d’expliquer le panier
- [ ] Être capable d’expliquer Hibernate et JPA
- [ ] Être capable d’expliquer les relations entre entités
- [ ] Être capable d’expliquer les transactions
- [ ] Être capable d’expliquer le paiement
- [ ] Être capable d’expliquer les tests JUnit et Mockito
- [ ] Être capable d’expliquer les parties développées par chaque membre
- [ ] Vérifier que tous les membres peuvent répondre aux questions sur le code

---

# 🔍 Final Verification

- [ ] Tester le site en utilisateur anonyme
- [ ] Tester le site en utilisateur authentifié
- [ ] Tester le site en administrateur
- [ ] Tester toutes les routes protégées
- [ ] Tester toutes les redirections
- [ ] Tester tous les formulaires
- [ ] Tester tous les tokens CSRF
- [ ] Tester toutes les validations
- [ ] Tester les erreurs fonctionnelles
- [ ] Tester les erreurs de stock
- [ ] Tester les erreurs de commande
- [ ] Tester le paiement réussi
- [ ] Tester le paiement annulé
- [ ] Tester le français
- [ ] Tester l’anglais
- [ ] Tester les traductions dynamiques
- [ ] Tester les messages d’erreur traduits
- [ ] Tester les promotions
- [ ] Tester la persistance après redémarrage
- [ ] Tester le panier pendant la session
- [ ] Vérifier que les mots de passe sont hashés
- [ ] Vérifier qu’un client ne peut pas accéder aux pages administrateur
- [ ] Vérifier qu’un utilisateur anonyme ne peut pas commander
- [ ] Vérifier qu’un client ne peut pas consulter la commande d’un autre client
- [ ] Exécuter tous les tests unitaires
- [ ] Vérifier que tous les tests passent
- [ ] Vérifier les logs et supprimer les traces temporaires
- [ ] Supprimer les `System.out.println` de débogage
- [ ] Vérifier qu’aucune exception technique n’est affichée à l’utilisateur
- [ ] Vérifier que l’application est prête pour la démonstration Enregistrer les modifications dans PostgreSQL
- [ ] Afficher un message après modification réussie
- [ ] Empêcher un utilisateur de modifier le compte d’un autre utilisateur

---

# 🏠 Global Website Structure

## 🧩 JSP Template

- ✅ Créer un template JSP général
- ✅ Créer un header commun
- ✅ Créer un footer commun
- ✅ Créer une zone dynamique pour le contenu principal
- ✅ Utiliser le template sur toutes les pages
- ✅ Éviter de dupliquer le header et le footer
- ✅ Centraliser les imports JSTL
- [ ] Centraliser les imports Spring Security JSP
- ✅ Configurer l’encodage UTF-8
- ✅ Utiliser les routes générées avec `<c:url>`

## 🧭 Global Navigation

Chaque page doit proposer au minimum :

- ✅ Un lien vers l’accueil
- ✅ Un lien vers la page de présentation de la société
- ✅ Un lien vers le catalogue
- ✅ Un lien vers le panier
- ✅ Le nombre total d’articles présents dans le panier
- ✅ Un lien vers l’inscription pour un utilisateur anonyme
- ✅ Un lien vers le login pour un utilisateur anonyme
- ✅ Un bouton de déconnexion pour un utilisateur authentifié
- ✅ Un lien vers le compte utilisateur pour un utilisateur authentifié
- ✅ Un choix de langue
- ✅ Un lien vers l’administration pour un administrateur
- [ ] Un message personnalisé pour un utilisateur connecté

---

# 🏠 Home & Company Pages

## 🏠 Home Page

- ✅ Créer une page d’accueil
- ✅ Présenter l’activité de la société
- ✅ Présenter les produits ou catégories principales
- ✅ Ajouter un accès rapide au catalogue
- [ ] Ajouter un accès rapide au panier
- [ ] Traduire le contenu statique de l’accueil

## 🏢 Company Page

- ✅ Créer une page statique « Notre société »
- ✅ Présenter l’entreprise
- ✅ Présenter son activité
- ✅ Présenter ses services
- [ ] Utiliser le template général
- [ ] Traduire la page en français et en anglais

---

# 📦 Catalog & Categories

## 🗂️ Categories

- ✅ Créer l’entité `Category`
- ✅ Ajouter un identifiant unique
- ✅ Ajouter un nom
- ✅ Ajouter une description
- ✅ Enregistrer les catégories dans PostgreSQL
- ✅ Créer `CategoryRepository`
- ✅ Créer une couche service pour les catégories si nécessaire
- ✅ Afficher dynamiquement les catégories depuis la base de données
- ✅ Ne pas coder les catégories directement dans les JSP
- ✅ Répercuter automatiquement les modifications de la base sur le site
- ✅ Proposer plusieurs catégories
- ✅ Prévoir plusieurs produits dans chaque catégorie
- [ ] Traduire dynamiquement les noms des catégories
- [ ] Traduire dynamiquement les descriptions des catégories

## 🔎 Category Selection

- ✅ Permettre à l’utilisateur de choisir une catégorie
- ✅ Charger les produits correspondant à la catégorie
- ✅ Afficher uniquement les produits de la catégorie choisie
- ✅ Prévoir une option pour afficher toutes les catégories
- [ ] Gérer une catégorie inexistante
- [ ] Gérer une catégorie sans produit
- [ ] Afficher un message traduit lorsqu’aucun produit n’est disponible

## 📦 Products

- ✅ Créer l’entité `Weapon`
- ✅ Ajouter un identifiant unique
- ✅ Ajouter un libellé (c'est le name)
- ✅ Ajouter une description
- ✅ Ajouter un prix unitaire
- ✅ Ajouter un stock
- ✅ Ajouter une référence unique
- ✅ Ajouter un fabricant
- ✅ Ajouter une catégorie
- ✅ Ajouter une image
- ✅ Enregistrer les produits dans PostgreSQL
- ✅ Créer `WeaponRepository`
- ✅ Afficher dynamiquement les produits depuis la base de données
- ✅ Répercuter automatiquement les modifications de la base sur le site
- ✅ Afficher au minimum le libellé dans la liste
- ✅ Afficher au minimum le prix unitaire dans la liste
- ✅ Ajouter un lien vers le détail du produit
- ✅ Afficher l’état du stock
- ✅ Signaler les produits en rupture de stock
- [ ] Empêcher l’ajout au panier d’un produit sans stock
- [ ] Trier les produits de façon cohérente
- [ ] Ajouter une recherche par nom
- ✅ Ajouter un filtre par catégorie
- [ ] Ajouter éventuellement d’autres filtres utiles

---

# 🔍 Product Details

- ✅ Créer une page de détail produit
- ✅ Afficher le nom du produit
- ✅ Afficher la description
- ✅ Afficher le prix
- ✅ Afficher la catégorie
- ✅ Afficher le fabricant
- ✅ Afficher la référence
- ✅ Afficher le stock
- ✅ Afficher l’image
- [ ] Gérer l’absence d’image
- ✅ Ajouter un champ quantité
- ✅ Définir une quantité minimale de 1
- ✅ Limiter la quantité au stock disponible
- ✅ Ajouter un bouton « Ajouter au panier »
- ✅ Ajouter un token CSRF au formulaire `POST`
- ✅ Refuser une quantité négative
- ✅ Refuser une quantité nulle
- ✅ Refuser une quantité supérieure au stock
- ✅ Afficher un message après ajout au panier
- [ ] Afficher un message d’erreur traduit en cas d’échec
- [ ] Gérer un identifiant produit inexistant

---

# 🛒 Shopping Cart

## 🧠 Cart Model

- ✅ Créer une classe `Cart`
- ✅ Créer une classe `CartItem`
- ✅ Stocker le produit dans chaque ligne
- ✅ Stocker la quantité dans chaque ligne
- ✅ Calculer le sous-total de chaque ligne
- ✅ Calculer le nombre total d’articles
- ✅ Calculer le montant total du panier
- ✅ Ajouter une méthode d’ajout de produit
- ✅ Ajouter une méthode de modification de quantité
- ✅ Ajouter une méthode de suppression d’article
- ✅ Ajouter une méthode pour vider le panier
- [ ] Ajouter une méthode pour vérifier si le panier est vide

## 🧑‍💻 Session Cart

- [ ] Stocker le panier dans la session HTTP
- [ ] Utiliser un composant `@SessionScope`
- ✅ Conserver le panier pendant toute la navigation
- ✅ Permettre à un utilisateur anonyme de posséder un panier
- ✅ Conserver le panier après authentification
- ✅ Rendre le nombre d’articles visible depuis toutes les pages
- ✅ Ne pas diminuer le stock lors de l’ajout au panier
- [ ] Diminuer le stock uniquement au moment prévu par le processus de commande

## 🛍️ Cart Page

- ✅ Créer une page de consultation du panier
- ✅ Afficher le libellé de chaque produit
- ✅ Afficher le prix unitaire
- ✅ Afficher la quantité
- ✅ Afficher le sous-total
- ✅ Afficher le montant total
- ✅ Ajouter un lien vers le détail du produit
- ✅ Ajouter un formulaire de modification de quantité
- ✅ Ajouter un bouton de suppression
- ✅ Ajouter un bouton pour vider le panier
- ✅ Ajouter un bouton pour continuer vers la commande
- ✅ Ajouter un token CSRF à chaque formulaire `POST`
- ✅ Afficher un message lorsque le panier est vide
- ✅ Empêcher les quantités nulles ou négatives
- ✅ Empêcher les quantités supérieures au stock
- [ ] Afficher des messages de succès
- [ ] Afficher des messages d’erreur traduits

---

# 📋 Checkout & Order Confirmation

## ✅ Checkout Page

- ✅ Créer une page `/checkout`
- ✅ Protéger `/checkout` avec Spring Security
- ✅ Autoriser uniquement les utilisateurs authentifiés
- ✅ Rediriger un utilisateur anonyme vers `/login`
- ✅ Restaurer `/checkout` après une connexion réussie
- ✅ Afficher le récapitulatif du panier
- ✅ Afficher les produits
- ✅ Afficher les quantités
- ✅ Afficher les prix unitaires
- ✅ Afficher les sous-totaux
- ✅ Afficher le montant total
- [ ] Afficher l’adresse de livraison
- [ ] Demander une confirmation avant l’enregistrement
- ✅ Ajouter un bouton « Confirmer la commande »
- ✅ Ajouter un token CSRF au formulaire de confirmation
- ✅ Empêcher la confirmation d’un panier vide

## 🧾 Order Persistence

- ✅ Créer l’entité `CustomerOrder`
- ✅ Créer l’entité `OrderItem`
- ✅ Relier une commande à ses lignes de commande
- ✅ Relier une commande au client authentifié
- ✅ Enregistrer la date de création
- ✅ Enregistrer le montant total
- ✅ Enregistrer le statut de paiement
- ✅ Enregistrer les lignes de commande
- ✅ Enregistrer le produit commandé
- ✅ Enregistrer la quantité commandée
- ✅ Enregistrer le prix appliqué au moment de la commande
- ✅ Enregistrer la commande avant le paiement
- ✅ Enregistrer toutes les lignes avant le paiement
- ✅ Utiliser une transaction pour valider la commande
- ✅ Vérifier le stock avant validation
- ✅ Réduire le stock après confirmation
- ✅ Annuler toute l’opération en cas d’erreur
- ✅ Vider le panier uniquement après une validation réussie
- ✅ Ne pas vider le panier si la commande échoue
- ✅ Rediriger vers la page de détail de la commande
- ✅ Afficher le numéro de commande
- ✅ Afficher toutes les lignes de la commande
- ✅ Afficher le montant total
- ✅ Afficher le statut payé/non payé
- [ ] Empêcher un utilisateur d’accéder à la commande d’un autre client

---

# 💳 Online Payment

## 💰 General Payment Requirements

- [ ] Proposer au moins une technique de paiement en ligne
- [ ] Utiliser PayPal ou une autre solution de paiement
- [ ] Enregistrer la commande avant le début du paiement
- [ ] Ne pas marquer directement la commande comme payée
- [ ] Créer un statut initial non payé
- [ ] Rediriger l’utilisateur vers le service de paiement
- [ ] Fournir le montant correct au service de paiement
- [ ] Fournir un identifiant de commande
- [ ] Fournir une devise
- [ ] Prévoir une URL de retour en cas de succès
- [ ] Prévoir une URL de retour en cas d’annulation
- [ ] Afficher une page après paiement réussi
- [ ] Afficher une page après paiement annulé
- [ ] Marquer la commande comme payée uniquement après validation du paiement
- [ ] Conserver la commande non payée si le paiement est annulé
- [ ] Empêcher le paiement d’une commande inexistante
- [ ] Empêcher le paiement d’une commande appartenant à un autre utilisateur
- [ ] Empêcher de payer deux fois la même commande
- [ ] Vérifier le montant côté serveur
- [ ] Ne pas faire confiance au montant envoyé par le navigateur
- [ ] Journaliser ou enregistrer la référence du paiement
- [ ] Tester le paiement avec un environnement Sandbox

## 🧪 PayPal Sandbox

- [ ] Créer un compte PayPal Developer
- [ ] Créer un compte vendeur Sandbox
- [ ] Créer un compte acheteur Sandbox
- [ ] Créer une application PayPal Sandbox
- [ ] Récupérer le Client ID
- [ ] Récupérer le Secret si nécessaire
- [ ] Ne pas enregistrer les secrets directement dans Git
- [ ] Stocker les identifiants dans la configuration
- [ ] Configurer l’adresse du vendeur Sandbox
- [ ] Envoyer le montant de la commande
- [ ] Envoyer le nom ou la référence de la commande
- [ ] Configurer `currency_code`
- [ ] Configurer l’URL `return`
- [ ] Configurer l’URL `cancel_return`
- [ ] Tester un paiement accepté
- [ ] Tester un paiement annulé
- [ ] Tester une erreur de paiement
- [ ] Vérifier la mise à jour du statut en base de données
- [ ] Vérifier que le panier reste vide après création de la commande
- [ ] Vérifier que la commande reste consultable après paiement

---

# 🏷️ Promotions

- [ ] Proposer au moins un système de promotion
- [ ] Définir les règles de promotion
- [ ] Faire preuve d’originalité dans les promotions
- [ ] Créer un service métier pour calculer les promotions
- [ ] Ne pas calculer les promotions directement dans la JSP
- [ ] Ne pas calculer les promotions directement dans le contrôleur
- [ ] Appliquer les promotions dans la couche business
- [ ] Afficher clairement le prix initial
- [ ] Afficher clairement la réduction
- [ ] Afficher clairement le prix final
- [ ] Intégrer la réduction au calcul du panier
- [ ] Intégrer la réduction au calcul de la commande
- [ ] Enregistrer le montant réellement facturé
- [ ] Empêcher un montant négatif
- [ ] Tester les promotions avec plusieurs cas
- [ ] Tester un panier sans promotion
- [ ] Tester un panier avec promotion
- [ ] Tester les limites des promotions
- [ ] Traduire les messages liés aux promotions

Exemples possibles :

- [ ] Promotion selon le montant total du panier
- [ ] Promotion selon la quantité achetée
- [ ] Promotion sur une catégorie
- [ ] Promotion sur certains produits
- [ ] Promotion temporaire
- [ ] Promotion réservée aux clients authentifiés

---

# 🌍 Internationalisation

## 🗣️ Language Selection

- [ ] Proposer au minimum le français
- [ ] Proposer au minimum l’anglais
- [ ] Ajouter un sélecteur de langue visible depuis toutes les pages
- [ ] Conserver la langue choisie pendant la session
- [ ] Utiliser la langue choisie lors de la navigation
- [ ] Prévoir une langue par défaut
- [ ] Ne pas perdre la langue après connexion
- [ ] Ne pas perdre la langue après déconnexion

## 📝 Static Labels

- [ ] Traduire les menus
- [ ] Traduire les boutons
- [ ] Traduire les titres
- [ ] Traduire les formulaires
- [ ] Traduire les labels
- [ ] Traduire les messages de confirmation
- [ ] Traduire les pages statiques
- [ ] Créer les fichiers de messages français
- [ ] Créer les fichiers de messages anglais

## 🗄️ Dynamic Labels

- [ ] Traduire les noms de catégories provenant de la base de données
- [ ] Traduire les descriptions de catégories
- [ ] Traduire certains noms ou descriptions de produits si nécessaire
- [ ] Concevoir une stratégie de stockage multilingue
- [ ] Charger la bonne traduction selon la langue active
- [ ] Prévoir une valeur de secours lorsqu’une traduction manque

## ⚠️ Error Messages

- [ ] Traduire les erreurs d’inscription
- [ ] Traduire les erreurs de connexion
- [ ] Traduire les erreurs du panier
- [ ] Traduire les erreurs de stock
- [ ] Traduire les erreurs de commande
- [ ] Traduire les erreurs de paiement
- [ ] Traduire les erreurs de validation
- [ ] Éviter les messages d’erreur codés directement en dur

---

# ✅ Data Validation

## 📝 Required Fields

- [ ] Vérifier que les champs obligatoires sont remplis
- [ ] Afficher un message pour chaque champ obligatoire vide
- [ ] Utiliser les annotations Jakarta Validation
- [ ] Utiliser `@Valid` dans les contrôleurs
- [ ] Utiliser `BindingResult`
- [ ] Empêcher l’enregistrement si le formulaire contient des erreurs

## 🔢 Numeric Fields

- [ ] Vérifier que les valeurs numériques contiennent des nombres valides
- [ ] Vérifier que le stock est positif ou nul
- [ ] Vérifier que le prix est positif
- [ ] Vérifier que la quantité commandée est strictement positive
- [ ] Vérifier que la quantité ne dépasse pas le stock
- [ ] Gérer les valeurs trop grandes
- [ ] Gérer les nombres décimaux correctement

## 🔑 Password Validation

- [ ] Ajouter deux champs de mot de passe lors de l’inscription
- [ ] Vérifier que les deux mots de passe sont identiques
- [ ] Définir une longueur minimale
- [ ] Définir une longueur maximale
- [ ] Hasher le mot de passe
- [ ] Ne jamais renvoyer le hash dans une page Web
- [ ] Ne jamais afficher le mot de passe dans les logs

## 📧 Email Validation

- [ ] Vérifier que l’adresse email est obligatoire
- [ ] Vérifier que le format de l’adresse email est valide
- [ ] Vérifier la présence de `@`
- [ ] Vérifier la présence d’un domaine
- [ ] Définir une longueur maximale
- [ ] Empêcher les valeurs vides ou composées uniquement d’espaces

## 📞 Phone & Postal Data

- [ ] Définir une longueur minimale pour le téléphone
- [ ] Définir une longueur maximale pour le téléphone
- [ ] Valider le format du téléphone
- [ ] Valider le code postal si un champ séparé est utilisé
- [ ] Définir une longueur minimale pour le code postal
- [ ] Définir une longueur maximale pour le code postal
- [ ] Valider l’adresse de livraison

## 👤 Login Validation

- [ ] Vérifier que le login n’est pas vide
- [ ] Définir une longueur minimale
- [ ] Définir une longueur maximale
- [ ] Vérifier que le login n’existe pas déjà
- [ ] Créer une contrainte unique en base de données
- [ ] Gérer proprement les conflits de login

---

# 🛡️ Security

## 🔒 Spring Security

- [ ] Configurer `SecurityFilterChain`
- [ ] Autoriser les ressources statiques
- [ ] Autoriser l’accueil
- [ ] Autoriser le catalogue
- [ ] Autoriser le détail des produits
- [ ] Autoriser le panier aux utilisateurs anonymes
- [ ] Autoriser le login
- [ ] Autoriser l’inscription
- [ ] Protéger le checkout
- [ ] Protéger les commandes
- [ ] Protéger l’administration
- [ ] Configurer `formLogin`
- [ ] Configurer `logout`
- [ ] Configurer les redirections après connexion
- [ ] Configurer les redirections après déconnexion
- [ ] Configurer les rôles
- [ ] Utiliser `hasRole("ADMIN")` pour l’administration
- [ ] Utiliser `authenticated()` pour les commandes

## 🛡️ CSRF

- [ ] Conserver la protection CSRF activée
- ✅ Ajouter un token CSRF aux formulaires `POST`
- ✅ Ajouter un token CSRF aux ajouts au panier
- ✅ Ajouter un token CSRF aux modifications de quantité
- ✅ Ajouter un token CSRF aux suppressions
- ✅ Ajouter un token CSRF à l’inscription
- ✅ Ajouter un token CSRF au login si nécessaire
- ✅ Ajouter un token CSRF au logout
- ✅ Ajouter un token CSRF à la confirmation de commande
- ✅ Ajouter un token CSRF aux actions administrateur
- ✅ Ne pas ajouter de token CSRF aux simples liens `GET`

## 💉 Injection Protection

- [ ] Utiliser JPA/Hibernate pour les requêtes
- [ ] Éviter les requêtes SQL construites par concaténation
- [ ] Utiliser des paramètres dans les requêtes
- [ ] Valider les identifiants reçus dans les URL
- [ ] Échapper correctement les données affichées
- [ ] Ne pas afficher de données non contrôlées avec du HTML brut
- [ ] Protéger l’application contre les injections SQL
- [ ] Protéger l’application contre les attaques XSS
- [ ] Ne pas exposer les secrets dans le code source
- [ ] Ne pas exposer les mots de passe dans les logs
- [ ] Ne pas faire confiance aux valeurs envoyées par le navigateur

## 🔐 Password Storage

- [ ] Utiliser un algorithme récent
- [ ] Utiliser BCrypt ou un équivalent reconnu
- [ ] Créer un bean `PasswordEncoder`
- [ ] Vérifier les mots de passe avec `matches`
- [ ] Vérifier que les hashes ne sont pas tronqués en base de données
- [ ] Prévoir une colonne suffisamment longue
- [ ] Vérifier qu’aucun mot de passe en clair n’est présent dans les scripts SQL

---

# 🏗️ Architecture

## 🧱 Three-Tier Architecture

- [ ] Structurer l’application en trois couches
- [ ] Créer une couche présentation
- [ ] Créer une couche métier
- [ ] Créer une couche accès aux données
- [ ] Éviter l’accès direct aux repositories depuis les JSP
- [ ] Éviter la logique métier dans les JSP
- [ ] Éviter les calculs métier dans les contrôleurs
- [ ] Centraliser les règles métier dans les services
- [ ] Utiliser l’injection de dépendances
- [ ] Réduire le couplage entre les couches
- [ ] Utiliser des interfaces lorsque cela apporte une abstraction utile

## 🎮 MVC Architecture

- [ ] Créer les classes Model
- [ ] Créer les contrôleurs Spring MVC
- [ ] Créer les vues JSP
- [ ] Transmettre les données via le `Model`
- [ ] Utiliser les contrôleurs pour gérer les requêtes
- [ ] Utiliser les JSP uniquement pour l’affichage
- [ ] Utiliser le pattern Post/Redirect/Get après les formulaires
- [ ] Utiliser `RedirectAttributes` pour les messages flash

## 📂 Suggested Packages

- [ ] Créer un package `controller`
- [ ] Créer un package `service`
- [ ] Créer un package `repository`
- [ ] Créer un package `model`
- [ ] Créer un package `security`
- [ ] Créer un package `config`
- [ ] Créer un package `session`
- [ ] Créer un package `validation`
- [ ] Créer un package `exception` si nécessaire

---

# 🗄️ Database & Persistence

## 🐘 PostgreSQL

- [ ] Créer une base de données PostgreSQL
- [ ] Configurer la connexion dans `application.yaml`
- [ ] Configurer l’URL JDBC
- [ ] Configurer le nom d’utilisateur
- [ ] Configurer le mot de passe via une méthode sécurisée
- [ ] Configurer Hibernate
- [ ] Configurer la stratégie DDL
- [ ] Vérifier les contraintes de colonnes
- [ ] Vérifier les contraintes d’unicité
- [ ] Vérifier les clés étrangères
- [ ] Vérifier les relations entre entités

## 🧬 Entities

- [ ] Définir les cascades de façon prudente
- [ ] Définir les longueurs maximales
- [ ] Utiliser `BigDecimal` pour les montants

## 🌱 Test Data

- [ ] Ajouter plusieurs catégories
- [ ] Ajouter plusieurs produits par catégorie
- [ ] Ajouter plusieurs utilisateurs
- [ ] Ajouter au moins un utilisateur normal
- [ ] Ajouter au moins un administrateur
- [ ] Ajouter des stocks variés
- [ ] Ajouter des produits en rupture de stock
- [ ] Ajouter des images
- [ ] Ajouter des données permettant de tester les promotions
- [ ] Ajouter des données permettant de tester le paiement
- [ ] Vérifier que les mots de passe de test sont hashés
- [ ] Fournir un script de création
- [ ] Fournir un script de population

---

# 🎨 JSP, HTML & CSS

## 🖼️ User Interface

- [ ] Créer une interface cohérente
- [ ] Utiliser CSS pour toute la mise en forme
- [ ] Éviter le style directement dans les balises HTML
- [ ] Créer une feuille CSS principale
- [ ] Structurer clairement le header
- [ ] Structurer clairement le contenu
- [ ] Structurer clairement le footer
- [ ] Styliser les formulaires
- [ ] Styliser les boutons
- [ ] Styliser les tableaux
- [ ] Styliser les messages de succès
- [ ] Styliser les messages d’erreur
- [ ] Afficher correctement les images
- [ ] Prévoir une interface utilisable sur différentes tailles d’écran
- [ ] Vérifier l’accessibilité minimale des formulaires
- [ ] Associer chaque champ à un `<label>`
- [ ] Ajouter des textes alternatifs aux images

## 📄 Dynamic Pages

- [ ] Rendre la page des catégories dynamique
- [ ] Rendre la liste des produits dynamique
- [ ] Rendre le détail produit dynamique
- [ ] Rendre le panier dynamique
- [ ] Rendre le checkout dynamique
- [ ] Rendre le détail de commande dynamique
- [ ] Ne pas coder les produits directement dans les JSP
- [ ] Ne pas coder les catégories directement dans les JSP

---

# 🧪 Tests

## ✅ Unit Tests

- [ ] Ajouter plusieurs tests unitaires de services
- [ ] Utiliser JUnit
- [ ] Utiliser Mockito
- [ ] Utiliser AssertJ
- [ ] Structurer les tests avec Arrange / Act / Assert
- [ ] Utiliser `@BeforeEach` lorsque nécessaire
- [ ] Mock les repositories
- [ ] Ne pas dépendre de PostgreSQL dans les tests unitaires

## 🛒 Cart Tests

- [ ] Tester l’ajout d’un nouveau produit
- [ ] Tester l’ajout d’un produit déjà présent
- [ ] Tester la modification de quantité
- [ ] Tester la suppression d’un produit
- [ ] Tester le vidage du panier
- [ ] Tester le montant total
- [ ] Tester la quantité totale
- [ ] Tester une quantité invalide
- [ ] Tester un panier vide

## 📦 Order Tests

- [ ] Tester la création d’une commande
- [ ] Tester la création des lignes de commande
- [ ] Tester le montant total
- [ ] Tester la diminution du stock
- [ ] Tester le vidage du panier
- [ ] Tester un panier vide
- [ ] Tester un stock insuffisant
- [ ] Tester un produit inexistant
- [ ] Tester qu’une erreur empêche la validation complète
- [ ] Tester que la transaction est cohérente

## 🏷️ Promotion Tests

- [ ] Tester une promotion applicable
- [ ] Tester une promotion non applicable
- [ ] Tester une promotion sur une limite exacte
- [ ] Tester plusieurs produits
- [ ] Tester un montant final correct
- [ ] Tester qu’un montant ne devient jamais négatif

## 👤 User Tests

- [ ] Tester l’inscription d’un utilisateur valide
- [ ] Tester un login déjà utilisé
- [ ] Tester deux mots de passe différents
- [ ] Tester un email invalide
- [ ] Tester les champs obligatoires
- [ ] Tester le hashage du mot de passe

## 💳 Payment Tests

- [ ] Tester la création d’un paiement
- [ ] Tester le retour de paiement réussi
- [ ] Tester le retour de paiement annulé
- [ ] Tester la mise à jour du statut payé
- [ ] Tester une commande déjà payée
- [ ] Tester une commande inexistante
- [ ] Tester une commande appartenant à un autre utilisateur

---

# ⚙️ Technical Stack

- [ ] Utiliser Java
- [ ] Utiliser Spring Boot
- [ ] Utiliser Apache Maven
- [ ] Utiliser Hibernate
- [ ] Utiliser Apache Tomcat
- [ ] Utiliser JUnit
- [ ] Utiliser Mockito
- [ ] Utiliser JSP
- [ ] Utiliser HTML
- [ ] Utiliser CSS
- [ ] Utiliser PostgreSQL
- [ ] Configurer correctement le `pom.xml`
- [ ] Vérifier que `mvn clean test` fonctionne
- [ ] Vérifier que `mvn clean package` fonctionne
- [ ] Vérifier que l’application démarre sans erreur
- [ ] Vérifier que le fichier WAR peut être construit

---

# 📦 Deliverables

- [ ] Déposer le code source complet sur GitLab
- [ ] Déposer le script de création de la base de données
- [ ] Déposer le script de population de la base de données
- [ ] Déposer le fichier `pom.xml`
- [ ] Déposer les JSP
- [ ] Déposer les fichiers CSS
- [ ] Déposer les images nécessaires
- [ ] Déposer les fichiers de traduction
- [ ] Déposer les fichiers de configuration nécessaires
- [ ] Déposer toutes les ressources nécessaires au fonctionnement
- [ ] Vérifier qu’aucun secret réel n’est présent dans Git
- [ ] Vérifier qu’aucun mot de passe en clair n’est présent dans Git
- [ ] Vérifier que le projet peut être cloné et démarré
- [ ] Ajouter un README avec les instructions d’installation
- [ ] Ajouter les instructions de création de la base
- [ ] Ajouter les comptes de démonstration
- [ ] Ajouter les instructions pour le paiement Sandbox
- [ ] Vérifier la date limite de remise
- [ ] Ne plus modifier le repository après la date limite

---

# 🎤 Oral Defence

- [ ] Préparer une démonstration complète
- [ ] Préparer un parcours utilisateur anonyme
- [ ] Préparer une inscription
- [ ] Préparer une connexion
- [ ] Préparer une démonstration du panier
- [ ] Préparer une démonstration de commande
- [ ] Préparer une démonstration du paiement
- [ ] Préparer une démonstration des promotions
- [ ] Préparer une démonstration multilingue
- [ ] Préparer une démonstration administrateur
- [ ] Préparer une démonstration des validations
- [ ] Préparer une démonstration des protections de sécurité
- [ ] Préparer une présentation de l’architecture MVC
- [ ] Préparer une présentation de l’architecture 3-tiers
- [ ] Être capable d’expliquer Spring Security
- [ ] Être capable d’expliquer les sessions
- [ ] Être capable d’expliquer le panier
- [ ] Être capable d’expliquer Hibernate et JPA
- [ ] Être capable d’expliquer les relations entre entités
- [ ] Être capable d’expliquer les transactions
- [ ] Être capable d’expliquer le paiement
- [ ] Être capable d’expliquer les tests JUnit et Mockito
- [ ] Être capable d’expliquer les parties développées par chaque membre
- [ ] Vérifier que tous les membres peuvent répondre aux questions sur le code

---

# 🔍 Final Verification

- [ ] Tester le site en utilisateur anonyme
- [ ] Tester le site en utilisateur authentifié
- [ ] Tester le site en administrateur
- [ ] Tester toutes les routes protégées
- [ ] Tester toutes les redirections
- [ ] Tester tous les formulaires
- [ ] Tester tous les tokens CSRF
- [ ] Tester toutes les validations
- [ ] Tester les erreurs fonctionnelles
- [ ] Tester les erreurs de stock
- [ ] Tester les erreurs de commande
- [ ] Tester le paiement réussi
- [ ] Tester le paiement annulé
- [ ] Tester le français
- [ ] Tester l’anglais
- [ ] Tester les traductions dynamiques
- [ ] Tester les messages d’erreur traduits
- [ ] Tester les promotions
- [ ] Tester la persistance après redémarrage
- [ ] Tester le panier pendant la session
- [ ] Vérifier que les mots de passe sont hashés
- [ ] Vérifier qu’un client ne peut pas accéder aux pages administrateur
- [ ] Vérifier qu’un utilisateur anonyme ne peut pas commander
- [ ] Vérifier qu’un client ne peut pas consulter la commande d’un autre client
- [ ] Exécuter tous les tests unitaires
- [ ] Vérifier que tous les tests passent
- [ ] Vérifier les logs et supprimer les traces temporaires
- [ ] Supprimer les `System.out.println` de débogage
- [ ] Vérifier qu’aucune exception technique n’est affichée à l’utilisateur
- [ ] Vérifier que l’application est prête pour la démonstration