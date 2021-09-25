package com.snowden.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue(value = "CE")
@NoArgsConstructor
@Getter
@Setter
public class CompteEpargne extends Compte{
    private double taux;

    public CompteEpargne(String codeCompte, Date creationDate, double solde, Client client, double taux){
        super(codeCompte, creationDate, solde, client);
        this.taux = taux;
    }
}
