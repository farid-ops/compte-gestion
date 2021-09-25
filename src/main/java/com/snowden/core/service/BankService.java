package com.snowden.core.service;

import com.snowden.core.model.Compte;
import com.snowden.core.model.Operation;
import org.springframework.data.domain.Page;

public interface BankService {

    Compte consulterCompte(String codeCompte);

    void verser(String compte, double montant);

    void retirer(String compte, double montant);

    void virer(String compte1, String compte2, double montant);

    Page<Operation> listeOperation(String codeCopte, int page, int size);
}
