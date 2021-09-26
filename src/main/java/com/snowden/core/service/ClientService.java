package com.snowden.core.service;

import com.snowden.core.model.Client;
import com.snowden.core.model.Compte;
import com.snowden.core.model.Role;
import org.springframework.data.domain.Page;

public interface ClientService {
    Client consulterClient(Long id);

    Client addClient(Client client);

    void deleteClient(Long id);

    Page<Compte> listeCompte(Long id, int page, int size);

    Role save(Role role);

    Client findByUsername(String username);

    Role findByRoleName(String rolename);

    void addRoleToClient(String username, String rolename);
}
