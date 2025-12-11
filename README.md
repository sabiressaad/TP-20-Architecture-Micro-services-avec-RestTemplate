# Architecture Microservices avec Spring Cloud

Ce projet démontre une architecture microservices complète utilisant Spring Boot et Spring Cloud Netflix.

## 📋 Description du Projet

Application distribuée composée de plusieurs microservices communiquant entre eux via une passerelle API (Gateway) et utilisant Eureka pour la découverte de services.

## 🏗️ Architecture

L'architecture comprend les composants suivants:

- **Eureka Server** (Port 8761): Service de découverte et d'enregistrement des microservices
- **Gateway** (Port 8888): Passerelle API pour le routage des requêtes
- **Service Client** (Port 8081): Microservice de gestion des clients
- **Service Car** (Port 8082): Microservice de gestion des voitures avec intégration au service Client

## 🚀 Technologies Utilisées

- Spring Boot 3.x
- Spring Cloud Netflix Eureka
- Spring Cloud Gateway
- Spring Data JPA
- MySQL
- Lombok
- RestTemplate pour la communication inter-services

## ⚙️ Prérequis

- JDK 17 ou supérieur
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, VS Code)

## 📦 Installation et Démarrage

### 1. Configuration de la Base de Données

Assurez-vous que MySQL est démarré et créez les bases de données nécessaires:

```sql
CREATE DATABASE clientservicedb;
CREATE DATABASE carservicedb;
```

### 2. Ordre de Démarrage des Services

**Important**: Démarrez les services dans cet ordre précis:

#### Étape 1: Démarrer Eureka Server
```bash
cd eureka-server
mvn spring-boot:run
```
Vérifiez que le serveur Eureka est accessible sur http://localhost:8761

#### Étape 2: Démarrer le Service Client
```bash
cd client
mvn spring-boot:run
```

#### Étape 3: Démarrer le Service Car
```bash
cd car
mvn spring-boot:run
```

#### Étape 4: Démarrer la Gateway
```bash
cd gateway
mvn spring-boot:run
```

### 3. Vérification du Déploiement

Accédez au tableau de bord Eureka: http://localhost:8761

Vous devriez voir tous les services enregistrés:
- GATEWAY
- SERVICE-CLIENT
- SERVICE-CAR

## 🔧 Configuration

### Configuration des Ports

| Service | Port | Description |
|---------|------|-------------|
| Eureka Server | 8761 | Serveur de découverte |
| Gateway | 8888 | Passerelle API |
| Service Client | 8081 | Gestion des clients |
| Service Car | 8082 | Gestion des voitures |

### Base de Données

Les configurations MySQL se trouvent dans les fichiers `application.yml` de chaque service:
- Username: root
- Password: (vide par défaut)
- Les bases sont créées automatiquement grâce à `createDatabaseIfNotExist=true`

## 📡 API Endpoints

### Via la Gateway (Recommandé)

#### Service Client
- **GET** `http://localhost:8888/SERVICE-CLIENT/api/client` - Liste tous les clients
- **GET** `http://localhost:8888/SERVICE-CLIENT/api/client/{id}` - Récupère un client par ID
- **POST** `http://localhost:8888/SERVICE-CLIENT/api/client` - Crée un nouveau client

#### Service Car
- **GET** `http://localhost:8888/SERVICE-CAR/api/car` - Liste toutes les voitures avec les détails des clients
- **GET** `http://localhost:8888/SERVICE-CAR/api/car/{id}` - Récupère une voiture par ID avec les détails du client

### Accès Direct (Alternative)

Vous pouvez aussi accéder directement aux services:
- Service Client: `http://localhost:8081/api/client`
- Service Car: `http://localhost:8082/api/car`

## 📝 Exemples de Requêtes

### Créer un Client

```bash
curl -X POST http://localhost:8888/SERVICE-CLIENT/api/client \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Jean Dupont",
    "age": 30
  }'
```

### Récupérer Tous les Clients

```bash
curl http://localhost:8888/SERVICE-CLIENT/api/client
```

### Récupérer Toutes les Voitures

```bash
curl http://localhost:8888/SERVICE-CAR/api/car
```

## 🔍 Fonctionnalités Principales

### Service Discovery avec Eureka
- Enregistrement automatique des services
- Health checks et monitoring
- Load balancing côté client

### API Gateway
- Routage dynamique basé sur Eureka
- Point d'entrée unique pour tous les services
- Configuration centralisée

### Communication Inter-Services
- RestTemplate configuré avec timeouts
- Gestion des erreurs et fallback
- Conversion automatique des DTOs

## 🐛 Dépannage

### Les services ne s'enregistrent pas dans Eureka
1. Vérifiez que Eureka Server est bien démarré en premier
2. Vérifiez les logs des services pour les erreurs de connexion
3. Assurez-vous que le port 8761 n'est pas bloqué par un firewall

### Erreur de connexion à MySQL
1. Vérifiez que MySQL est démarré
2. Vérifiez les credentials dans `application.yml`
3. Assurez-vous que les bases de données existent

### Gateway ne route pas les requêtes
1. Vérifiez que `lower-case-service-id` est à `true` dans la configuration
2. Attendez quelques secondes après le démarrage pour que la découverte soit complète
3. Consultez les logs de la Gateway

## 📚 Structure du Projet

```
tp20/
├── eureka-server/          # Service de découverte Eureka
│   └── src/main/
│       ├── java/
│       └── resources/
│           └── application.yml
├── gateway/                # API Gateway
│   └── src/main/
│       ├── java/
│       └── resources/
│           └── application.yml
├── client/                 # Microservice Client
│   └── src/main/
│       ├── java/
│       │   └── com/example/clientservice/
│       │       ├── controllers/
│       │       ├── entities/
│       │       ├── repositories/
│       │       └── services/
│       └── resources/
│           └── application.yml
└── car/                    # Microservice Car
    └── src/main/
        ├── java/
        │   └── com/example/car/
        │       ├── controllers/
        │       ├── entities/
        │       ├── models/
        │       ├── repositories/
        │       └── services/
        └── resources/
            └── application.yml
```

## 🎯 Points Clés d'Apprentissage

1. **Service Discovery**: Utilisation d'Eureka pour l'enregistrement dynamique des services
2. **API Gateway**: Routage intelligent et point d'entrée unique
3. **Communication Inter-Services**: RestTemplate pour les appels HTTP synchrones
4. **Gestion des Données**: JPA/Hibernate pour la persistance
5. **Configuration Distribuée**: Fichiers YAML pour chaque service

## 📖 Ressources Supplémentaires

- [Spring Cloud Netflix Documentation](https://spring.io/projects/spring-cloud-netflix)
- [Spring Cloud Gateway Documentation](https://spring.io/projects/spring-cloud-gateway)
- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)

## 👨‍💻 Auteur

Développé dans le cadre du TP 20 - Architecture Microservices

## 📄 Licence

Ce projet est développé à des fins éducatives.
