package com.snowden.core.model;

import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("retrait")
@NoArgsConstructor
public class Retrait extends Operation{

    public Retrait(Long operationNumber, Date operationDate, double montant, Compte compte){
        super(operationNumber, operationDate, montant, compte);
    }
}
