package fr.niji.example.trainresa.config;

import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class TestDataSourceConfiguration {

    // Cette configuration est automatiquement appliquée pour les tests
    // d'intégration
    // Elle permet d'éviter les conflits entre H2 et PostgreSQL

}