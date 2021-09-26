package com.snowden.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Client implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    private Date creationDate;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private Collection<Compte> comptes = Collections.emptyList();

    public Client(String firstname, String lastname, Date creationDate, String username, String password){
        this.firstname = firstname;
        this.lastname = lastname;
        this.creationDate = creationDate;
        this.username = username;
        this.password = password;
    }
}
