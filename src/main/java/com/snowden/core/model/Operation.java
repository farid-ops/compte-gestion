package com.snowden.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "operations")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "operation_type",length = 14)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class Operation implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long operationNumber;
    protected Date operationDate;
    protected double montant;
    @ManyToOne
    @JoinColumn(name = "fk_compte")
    protected Compte compte;
}
