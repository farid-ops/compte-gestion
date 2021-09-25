package com.snowden.core.service;

import com.snowden.core.dao.ClientRepository;
import com.snowden.core.dao.CompteRepository;
import com.snowden.core.dao.OperationRepository;
import com.snowden.core.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class BankServiceImpl implements BankService {

    private final ClientRepository clientRepository;
    private final CompteRepository compteRepository;
    private final OperationRepository operationRepository;

    public BankServiceImpl(ClientRepository clientRepository,
                           CompteRepository compteRepository,
                           OperationRepository operationRepository) {
        this.clientRepository = clientRepository;
        this.compteRepository = compteRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    public Compte consulterCompte(String codeCompte) {
        Compte compte = this.compteRepository.findById(codeCompte).get();
        if (compte != null) {
            return compte;
        }
        throw new RuntimeException("compte introuvable");
    }

    @Override
    public void verser(String compte, double montant) {
        Compte compte1 = this.consulterCompte(compte);
        Operation versement = new Versement(null, new Date(), montant, compte1);
        compte1.setSolde(compte1.getSolde() + montant);
        this.compteRepository.save(compte1);
        this.operationRepository.save(versement);
    }

    @Override
    public void retirer(String compte, double montant) {
        Compte compte1 = this.consulterCompte(compte);
        Retrait  retrait = new Retrait(null, new Date(), montant, compte1);
        double caisse = 0.0;
        if (compte1 instanceof CompteCourant)
            caisse = ((CompteCourant)compte1).getDecouvert();
        if (compte1.getSolde()+caisse < montant)
            throw new RuntimeException("Solde insuffisant");
        compte1.setSolde(compte1.getSolde() - montant);
        this.compteRepository.save(compte1);
        this.operationRepository.save(retrait);
    }

    @Override
    public void virer(String compte1, String compte2, double montant) {
         this.retirer(compte1, montant);
         this.verser(compte2, montant);
    }

    @Override
    public Page<Operation> listeOperation(String codeCompte, int page, int size) {
        Pageable pageable1 = PageRequest.of(page, size);
        return this.operationRepository.listeOperation(codeCompte, pageable1);
    }
}
