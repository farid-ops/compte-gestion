package com.snowden.core.model;

import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("versement")
@NoArgsConstructor
public class Versement extends Operation{

    public Versement(Long operationNumber, Date operationDate, double montant, Compte compte){
        super(operationNumber, operationDate, montant, compte);
    }
}
