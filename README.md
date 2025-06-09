# 🚄 Train Reservation System

Système de réservation de billets de train développé avec Spring Boot et une architecture Clean Architecture.

## 🏗️ Architecture

Le projet suit les principes de la Clean Architecture avec :
- **Domain** : Entités métier, Use Cases, interfaces de repositories
- **Infrastructure** : Implémentations JPA, Controllers REST, mappers
- **Tests** : Tests unitaires (Use Cases) et tests d'intégration (repositories)

## 🛠️ Stack Technique

- **Java 21** avec Eclipse Temurin
- **Spring Boot 3.2.1** (Web, Data JPA, Validation)
- **PostgreSQL 15** comme base de données
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

### Monitoring
- `GET /actuator/health` - État de santé de l'application
- `GET /actuator/info` - Informations sur l'application
- `GET /actuator/metrics` - Métriques de l'application

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
./gradlew :app:test --tests "com.example.app.usecase.*"

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

## 📊 Architecture des Données

### Entités principales
- **Journey** : Trajet avec gares de départ/arrivée, horaires, places disponibles
- **Reservation** : Réservation associée à un trajet et un passager

### Base de données
- **journeys** : Stockage des trajets
- **reservations** : Stockage des réservations

## 🔧 Configuration

### Variables d'environnement
- `SPRING_DATASOURCE_URL` : URL de la base de données
- `SPRING_DATASOURCE_USERNAME` : Utilisateur de la base
- `SPRING_DATASOURCE_PASSWORD` : Mot de passe de la base
- `SPRING_PROFILES_ACTIVE` : Profil Spring actif

### Profils Spring
- **default** : Développement local
- **docker** : Environnement containerisé
