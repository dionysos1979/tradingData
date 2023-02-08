package com.traderpatient.tradingdata.model.base;

public class ScoreValue {
    /**
     * Méthode de calcul d'un score Value : cf approche du fonds Indépendance et Expansion
     *
     * Critères d'exclusion :
     * ---------------------
     * - Pas d'investissement dans une entreprise dont le CA baisse
     * - Pas d'investissement dans les banques
     *
     * Critères de ranking :
     * ---------------------
     * - Rentabilité des capitaux investis élevés (> 20%)
     * - Marge opérationnelle élevée pour le secteur
     *   5% de base et on peut descendre à 4%
     * - Momentum élevé : Ne pas acheter des titres dont le cours baisse significativement :
     *   ex si le cours décale de 20% P/R au marché, c'est qu'il y a un loup ... Donc attendre la prochaine publication
     *   Dans ce cas le marché est efficace.
     *   Donner une prime aux sociétés dont le cours monte
     * - Valorisation faible P/R au marché ou P/R au secteur :
     *      - PER faible (decoté P/R au marché en dépit d'une forte rentabilité)
     *      - Cours sur Autofinancement faible (min décote de 30%)
     * - Pas ou peu de dettes car dettes signifie que le FCF est insuffisant
     * - Prime accordée aux sociétés familiales
     *
     * Résultats :
     * ---------------------
     * trouver sur 1000 sociétés cotées 60 qui répondent à ces critères
     *
     */
}
