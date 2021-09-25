package com.snowden.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("CC")
@NoArgsConstructor
@Getter
@Setter
public class CompteCourant extends Compte{
    private double decouvert;

    public CompteCourant(String codeCompte, Date creationDate, double solde,  Client client, double decouvert){
        super(codeCompte, creationDate, solde, client);
        this.decouvert = decouvert;
    }
}
