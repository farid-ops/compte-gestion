package com.snowden.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Entity
@Table(name = "comptes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_compte", discriminatorType = DiscriminatorType.STRING, length = 4)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class Compte {
    @Id @Column(nullable = false)
    protected String codeCompte;
    protected Date creationDate;
    protected double solde;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_client")
    protected Client client;
    @OneToMany(mappedBy = "compte", fetch = FetchType.LAZY)
    protected Collection<Operation> operations = Collections.emptyList();

    public Compte(String codeCompte, Date creationDate, double solde, Client client) {
        this.codeCompte = codeCompte;
        this.creationDate = creationDate;
        this.solde = solde;
        this.client = client;
    }
}
