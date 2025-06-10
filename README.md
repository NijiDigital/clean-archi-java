# 🚄 Train Reservation System

Système de réservation de billets de train rudimentaire développé avec Spring Boot et la Clean Architecture.

## 🏗️ Architecture

Le projet suit les principes de la Clean Architecture avec :
- **Domain** : Entités métier, Use Cases, interfaces de repositories
- **Infrastructure** : Implémentations JPA, Controllers REST, mappers
- **Tests** : Tests unitaires (Use Cases) et tests d'intégration (repositories)

## 🛠️ Stack Technique

- **Java 21** avec Eclipse Temurin
- **Spring Boot 3.X** (Web, Data JPA, Validation)
- **PostgreSQL 16** comme base de données
- **Lombok** pour réduire le boilerplate
- **Gradle 8.8** pour la gestion des dépendances
- **Docker & Docker Compose** pour la containerisation
- **Testcontainers** pour les tests d'intégration

## 🚀 Démarrage Rapide

### Prérequis
- **Docker** et **Docker Compose** installés
- **Git** pour cloner le projet

### 1. Cloner le projet
```bash
git clone <repository-url>
cd cleanarchi
```

### 2. Démarrer avec Docker Compose
```bash
# Construire et démarrer les services
docker-compose up --build

# Ou en mode détaché
docker-compose up --build -d
```

### 3. Vérifier le démarrage
```bash
# Health check de l'application
curl http://localhost:8080/actuator/health

# Lister les trajets disponibles
curl http://localhost:8080/api/journeys
```

## 🔗 Endpoints API

### Trajets
- `GET /api/journeys` - Liste tous les trajets disponibles

### Réservations
- `POST /api/reservations` - Créer une réservation

**Exemple de réservation :**
```bash
curl -X POST http://localhost:8080/api/reservations \
  -H "Content-Type: application/json" \
  -d '{
    "journeyId": "<journey-id>",
    "passengerName": "John Doe"
  }'
```

## 🛠️ Développement Local

### Prérequis
- **Java 21**
- **Gradle 8.8+**
- **PostgreSQL 15** (ou Docker)

### Base de données locale
```bash
# Démarrer uniquement PostgreSQL
docker-compose up postgres -d
```

### Lancer l'application
```bash
# Compiler et lancer les tests
./gradlew clean build

# Démarrer l'application
./gradlew :app:bootRun
```

### Tests
```bash
# Tests unitaires uniquement
./gradlew :app:test --tests "fr.niji.example.trainresa.usecase.*"

# Tests d'intégration (nécessite Docker)
./gradlew :app:test --tests "*IntegrationTest"

# Tous les tests
./gradlew :app:test
```

## 🐳 Commandes Docker Utiles

```bash
# Arrêter les services
docker-compose down

# Arrêter et supprimer les volumes
docker-compose down -v

# Voir les logs
docker-compose logs -f app

# Reconstruire l'image
docker-compose build app

# Redémarrer uniquement l'application
docker-compose restart app
```

## 🔧 Configuration

### Variables d'environnement
- `SPRING_DATASOURCE_URL` : URL de la base de données
- `SPRING_DATASOURCE_USERNAME` : Utilisateur de la base
- `SPRING_DATASOURCE_PASSWORD` : Mot de passe de la base
- `SPRING_PROFILES_ACTIVE` : Profil Spring actif

### Profils Spring
- **default** : Développement local
- **docker** : Environnement containerisé
