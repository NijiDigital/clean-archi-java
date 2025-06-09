# Cursor Rules pour la Clean Architecture

## Structure
Le projet sera une adaptation de la clean architecture avec la structure suivante :

```
src/
 ├── main/
 │   ├── java/
 │   │   └── com/example/app/     (root package d'exemple)
 │   │       ├── domain
 │   │       │   ├── entity
 │   │       │   │   └── XXX.java                      
 │   │       │   ├── exception       
 │   │       │   │   ├── InvalidDomainException.java   (exception métier de base)
 │   │       │   │   └── UserNotFoundException.java    (exception métier spécifique)
 │   │       │   ├── repository                        (équivalent aux gateways)
 │   │       │   │   └── XXXRepository.java            (interface de repository)
 │   │       │   ├── usecase                           (Use Cases en Pure Java avec @Transactional)
 │   │       │   │   └── UseCaseExample.java
 │   │       │   └── valueobject
 │   │       │       ├── ValueObjectName.java                      
 │   │       │       └── XXXId.java                      
 │   │       └── infrastructure
 │   │           ├── Application.java             (SpringBoot Main entry point)
 │   │           ├── config                       (SpringBoot configs et instanciation des Use Cases)
 │   │           ├── persistence
 │   │           │   ├── entity
 │   │           │   │   └── XXXJpaEntity.java    (classe entité JPA)
 │   │           │   ├── mapper
 │   │           │   │   └── XXXJpaMapper.java    (classe de mapping entre une entité du domaine et entité JPA)
 │   │           │   └── XXXJpaRepository.java    (implementation JPA du repository)
 │   │           └── web
 │   │               ├── dto
 │   │               │   ├── XXXDTO.java          (DTO utilisés en entrée et sortie des controlleurs)
 │   │               ├── mapper
 │   │               │   └── XXXMapper.java       (classe de mapping entre une entité du domaine et un DTO)
 │   │               ├── GlobalExceptionHandler.java    (gestionnaire global d'exceptions)
 │   │               └── XXXController.java       
 │   └── resources/
 │       └── application.yml    (Application configuration)
 └── test/
     ├── java/
     │   └── com/example/app/
     │       └── usecase/         (Usecase unit tests)
     │       └── infrastructure/         (jpa queries integration tests)
     └── resources/
         └──application.yml      (Test-specific configuration)
```

## Convention de nommage
-Utiliser le PascalCase pour les classes, le CamelCase pour méthodes et variables, le snake case pour les constantes

## Style de programmation
-Utilise le plus possible le style programmation fonctionnel (java streams)

## Composants de l'architecture

### Sens des dépendances
- le domaine ne dépend de rien d'autre
- les use cases dépendent du domaine et de l'annotation @Transactional uniquement
- l'infrastructure dépend des use case et du domaine (utilise l'injection de dépendance)

### Domaine

#### Entités
- classe java immutable respectant le principe d'encapsulation. 

#### Exceptions
- **InvalidDomainException** : exception métier de base pour les erreurs de validation
- Remplace toujours `IllegalArgumentException` par `InvalidDomainException`
- Les erreurs de validations doivent remonter sous forme d'exception spécifique

#### Value objects
- Servent à modéliser des objets de base comme les ID, les noms de personne, les adresses, les emails, etc...
- Reposer au maximum sur ces objets plutot que les types de base de java (Typage plus fort)
- implémenter sous forme de record pour l'aspect immutable et plus lisible (ne pas implémenter equals, hashCode et toString)
- Ils incluent leur validation dans le constructeur (compact) avec `InvalidDomainException`
- utiliser les méthodes factory notamment pour la création à partir de string (of(string))

#### Value object ID
- record spécifique pour chaque entité (Typage fort)
- utiliser les UUID par défaut comme représentation interne
- possède des méthodes factory generate() et (of(string))
- validation avec `InvalidDomainException`

### Use Case
- **Pure Java** : classe d'orchestration traitant un seul cas d'usage **SANS annotation @Service**
- La méthode `execute` est annotée avec `@Transactional` (jakarta.transaction.Transactional)
- possède un **record** interne pour la requete (UseCaseNameRequest) et un pour le résultat (UseCaseNameResult)
- Utilise Lombok pour @RequiredArgsConstructor uniquement
- Placés dans le package `domain.usecase`
- Instanciés via configuration Spring (@Bean)

### Infrastructure

#### Configuration des Use Cases
- Les Use Cases sont instanciés dans une classe de configuration Spring (@Configuration)
- Utilise @Bean pour créer les instances des Use Cases
- Injection des dépendances (repositories) via les constructeurs

#### Frameworks et versions
-Utilise Java 21, SpringBoot 3.X, et une base postgresql

#### Controlleurs
- Facade REST délégant directement aux Use Cases
- Utilise les DTO en entrée et sortie de chaque endpoint
- **N'effectue PAS de gestion manuelle des exceptions** (délégué au GlobalExceptionHandler)
- **N'effectue PAS de mapping manuel** (délégué aux mappers dans le package web.mapper)

#### Mapping d'objets
- Les mappers (entité-JPA et entité-DTO) sont implémentés avec des **classes simples** (@Component)
- **Mappers JPA** : dans le package `persistence.mapper`, convertissent entre entités domaine et entités JPA
- **Mappers Web** : dans le package `web.mapper`, convertissent entre entités domaine et DTOs
- Les controllers et repositories utilisent leurs mappers respectifs via injection de dépendance
- Utilise le mapping automatique quand les types sont compatibles, converters manuels sinon

#### Traitement des erreurs
- Utilise un **GlobalExceptionHandler** (@ControllerAdvice) qui traduit automatiquement les erreurs de domaine en erreurs HTTP
- **InvalidDomainException** → 400 Bad Request
- **NotFoundException** → 404 Not Found  
- **ConflictException** → 409 Conflict
- Les controllers n'ont pas de try-catch, les exceptions remontent naturellement

#### SpringBoot
- Utilise JPA pour l'implémentation des entités
- la frontière de la transaction se situera au niveau des méthodes execute des Use Cases
- préfère l'injection par constructeur (généré par lombok) plutôt qu'Autowired

#### Transactions
Les Use Cases gèrent leurs propres transactions via `@Transactional` sur la méthode `execute`

#### Lombok
- Utilise lombok pour améliorer la lisibilité, y compris dans la partie domaine

## Tests
- Crée des tests unitaire que pour les Use Case (ils couvriront toute la couche domaine également). Traite le cas de succès ainsi que tous les cas d'erreur.
- Les tests des Use Cases sont en Pure Java (pas de Spring Context)
- Crée des tests d'intégration pour les requetes JPQL spécifiques, et les API externes. Utilise pour cela la librairie testcontainers
- Utilise le formalise AAA

## Exemple de Structure

### Mapper JPA (classe simple)
```java
@Component
public class ExampleJpaMapper {

    public Example toDomain(ExampleJpaEntity jpaEntity) {
        return Example.builder()
                .id(ExampleId.of(jpaEntity.getId()))
                .name(jpaEntity.getName())
                .build();
    }

    public ExampleJpaEntity toJpaEntity(Example domain) {
        return ExampleJpaEntity.builder()
                .id(domain.getId().toString())
                .name(domain.getName())
                .build();
    }
}
```

### Mapper Web (classe simple)
```java
@Component
public class ExampleMapper {

    public ExampleDTO toDTO(Example example) {
        return ExampleDTO.builder()
                .id(example.getId().toString())
                .name(example.getName())
                .build();
    }

    public List<ExampleDTO> toDTO(List<Example> examples) {
        return examples.stream()
                .map(this::toDTO)
                .toList();
    }
}
```

### GlobalExceptionHandler
```java
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDomainException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDomainException(InvalidDomainException e) {
        log.warn("Invalid domain exception: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("INVALID_INPUT", e.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        log.warn("Entity not found: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("NOT_FOUND", e.getMessage()));
    }

    public record ErrorResponse(String code, String message) {
    }
}
```

### Use Case (Pure Java avec @Transactional et records)
```java
@RequiredArgsConstructor
public class ExampleUseCase {
    
    private final ExampleRepository repository;
    
    @Transactional  // jakarta.transaction.Transactional
    public ExampleResult execute(ExampleRequest request) {
        // logique métier avec transaction automatique
        return new ExampleResult(/* ... */);
    }
    
    public record ExampleRequest(String parameter1, Long parameter2) {
    }
    
    public record ExampleResult(String result) {
    }
}
```

### Configuration Infrastructure
```java
@Configuration
public class UseCaseConfiguration {
    
    @Bean
    public ExampleUseCase exampleUseCase(ExampleRepository repository) {
        return new ExampleUseCase(repository);
    }
}
```

### Controller (sans gestion d'exception ni mapping)
```java
@RestController
@RequiredArgsConstructor
public class ExampleController {
    
    private final ExampleUseCase exampleUseCase;
    private final ExampleMapper exampleMapper;  // Injection du mapper
    
    @PostMapping("/example")
    public ResponseEntity<ExampleDTO> createExample(@RequestBody ExampleRequestDTO dto) {
        // Pas de try-catch, pas de mapping manuel
        var request = new ExampleUseCase.ExampleRequest(dto.getParam1(), dto.getParam2());
        var result = exampleUseCase.execute(request);
        return ResponseEntity.ok(exampleMapper.toDTO(result.example()));
    }
}
```

## Exemple de value object avec InvalidDomainException
```java
public record Email(String value) {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);

    public Email {
        validateEmail(value);
    }

    private static void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new InvalidDomainException("Email cannot be null or empty");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidDomainException("Invalid email format");
        }
    }

    public static Email of(String value) {
        return new Email(value);
    }
}
```
