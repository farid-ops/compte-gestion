package com.snowden.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Client implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private Collection<Compte> comptes = Collections.emptyList();

    public Client(String username, String password){
        this.username = username;
        this.password = password;
    }
}
