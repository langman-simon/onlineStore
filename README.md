# Hyperion – boutique airsoft (TEB3005-NI)

1. Lancer Docker Desktop, puis : `docker compose up -d`
2. Ouvrir le projet dans IntelliJ (Java 26) et recharger Gradle 
3. Lancer l'application : `./gradlew bootRun` → http://localhost:8080
4. Comptes de test : `user` / `mdp` (client) et `admin` / `mdp` (admin)
5. Avant chaque push : `./gradlew clean build` doit afficher `BUILD SUCCESSFUL`