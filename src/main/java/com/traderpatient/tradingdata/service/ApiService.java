package com.traderpatient.tradingdata.service;

import com.traderpatient.tradingdata.dao.CompteurRequeteRepository;
import com.traderpatient.tradingdata.model.CompteurRequete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class ApiService {

    Logger logger = LoggerFactory.getLogger(ApiService.class);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
    private CompteurRequeteRepository compteurRepository;

    public ApiService( CompteurRequeteRepository compteurRepository) {
        this.compteurRepository = compteurRepository;
    }

    public boolean isCompteurRequetesDepasse(String site, String apiKey, int maxReqAutorised){
        // Vérification des compteurs de requetes par API
        CompteurRequete compteur = compteurRepository.findBySiteWebAndDateAndApikey(site, new Date(), apiKey);

        // ligne n'existe pas => on la créé
        if (compteur == null) {
            compteur = initCompteurRequetes(site, apiKey);
        }
        // ligne n'existe pas => on la créé
        else if ( compteur.getNbRequetes() >= maxReqAutorised) {
            logger.error("Nombre maximal de requêtes atteint pour ce compteur:" + compteur);
            return true;
        }

        // Le compteur n'est pas dépassé, on peut l'incrémenter
        incrementeCompteurRequetes(site, apiKey,maxReqAutorised);

        return false;
    }

    public void incrementeCompteurRequetes(String site, String apiKey, int maxReqAutorised){
        CompteurRequete compteur = compteurRepository.findBySiteWebAndDateAndApikey(site, new Date(), apiKey);
        // ligne n'existe pas => on la créé
        if (compteur == null){
            compteur = initCompteurRequetes(site, apiKey);
        }
        compteur.setNbRequetes(compteur.getNbRequetes() + 1);
        CompteurRequete compteurNew = compteurRepository.save(compteur);
        logger.info("Incrémente Compteur:" + compteurNew.toString());
    }

    public CompteurRequete initCompteurRequetes(String site, String apiKey){
        CompteurRequete compteur = new CompteurRequete(new Date(), site, apiKey, 0d);
        compteur = compteurRepository.save(compteur);
        logger.info("Création du compteur:" + compteur);
        return compteur;
    }
}
