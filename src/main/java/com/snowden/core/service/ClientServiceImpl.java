package com.snowden.core.service;

import com.snowden.core.dao.ClientRepository;
import com.snowden.core.dao.CompteRepository;
import com.snowden.core.model.Client;
import com.snowden.core.model.Compte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final CompteRepository compteRepository;

    public ClientServiceImpl(ClientRepository clientRepository,
                             CompteRepository compteRepository){
        this.clientRepository = clientRepository;
        this.compteRepository = compteRepository;
    }

    @Override
    public Client consulterClient(Long id) {
        Client client = this.clientRepository.findById(id).get();
        if (client != null)
            return client;
        throw new RuntimeException("client introuvable");
    }

    @Override
    public Client addClient(Client client) {
        return this.clientRepository.save(client);
    }

    @Override
    public void deleteClient(Long id) {
        this.clientRepository.deleteById(id);
    }

    @Override
    public Page<Compte> listeCompte(Long id, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return this.compteRepository.listeCompte(id, pageRequest);
    }
}
