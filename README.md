# Hyperion - Local Setup Guide

Welcome to the **Hyperion** project! Follow this guide to set up your local development environment and get the application running smoothly.

---

## web url (homepage)

http://localhost:8080/

## 🛠️ Prerequisites

Before running the application, ensure you have the following installed on your machine:
* **Java Development Kit (JDK) 26**
* **Apache Maven 3.9+** (or use IntelliJ's built-in Maven)
* **PostgreSQL Server**

---

## 💾 1. Database Setup (PostgreSQL)

The application requires a local PostgreSQL database instance.

### Step A: Create the Database & User Configuration
Open your PostgreSQL terminal (`psql`), pgAdmin, or DataGrip, and execute the following commands:

```sql
-- Create the project database
CREATE DATABASE hyperion;

-- Set the password for the default 'postgres' user
ALTER USER postgres WITH PASSWORD 'mdp';
```

> ⚠️ **Important:** We use **`mdp`** as the default local password for simplicity across the team. Make sure your local PostgreSQL instance matches this setup.

---

## ⚙️ 2. Application Configuration

Verify that your `src/main/resources/application.properties` file contains the correct database credentials:

```properties
spring.application.name=hyperion

# PostgreSQL Local Connection
spring.datasource.url=jdbc:postgresql://localhost:5432/hyperion
spring.datasource.username=postgres
spring.datasource.password=mdp

# Hibernate DDL Auto-Generation
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 🚀 Building the Application

### Using the Command Line
Navigate to the project's root directory and run:
```bash
mvn clean install
```

### Using IntelliJ IDEA
1. Open the project in IntelliJ.
2. Go to `File` ➔ `Project Structure` ➔ `Project` and ensure the **Project SDK** is set to **Java 26**.
3. Open the **Maven** tool window on the right side of the IDE.
4. Expand **Lifecycle**, then double-click **`clean`**, followed by **`install`**.

> 💡 **Tip:** During the first build, Hibernate will automatically connect to your local PostgreSQL server and generate all the required database tables for you.

---

## 🌿 Git Workflow (feature & Main)

### Travailler sur la branche feature
Fais tes modifications de code dans ton IDE, puis sauvegarde ton travail localement :
```bash
git commit -am "Mon message de commit clair"
```

### Récupérer le main distant et fusionner
Sans quitter ta branche `feature`, va chercher les mises à jour du serveur et fusionne-les pour anticiper les conflits :
```bash
git fetch origin
git merge origin/main -m "sync: fusion du main distant dans la branche feature"
```

### Résoudre les conflits (Si nécessaire)
Si Git indique des conflits, règle-les directement dans IntelliJ, puis valide la résolution :
```bash
git commit -am "chore: résolution des conflits avec le main distant"
```

### Déployer sur le main local et pousser
Une fois que ta branche `feature` est propre et à jour, bascule sur `main` pour y injecter ton travail et le pousser sur le serveur :
```bash
git switch main
git merge feature -m "merge branch 'feature' into main"
git push
git switch feature
```

---

### if yours coworker need to see your code PUSH/DELETE your local feature branch to remote repository. 
```bash
git push -u origin template
git push origin --delete template
```