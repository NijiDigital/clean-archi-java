package com.example.app.domain.query;

import java.util.List;

import com.example.app.domain.query.view.JourneyWithAvailability;

/**
 * Interface pour les requêtes de lecture des journeys
 * Suit le pattern CQRS - séparation des queries et des commandes
 */
public interface JourneyQuery {

    /**
     * Récupère tous les journeys avec le comptage des réservations actives en une
     * seule requête optimisée
     * 
     * @return Liste des journeys avec leurs places disponibles calculées
     */
    List<JourneyWithAvailability> findAllJourneysWithAvailability();
}