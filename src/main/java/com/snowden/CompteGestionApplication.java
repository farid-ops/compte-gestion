package com.snowden;

import com.github.javafaker.Faker;
import com.snowden.core.dao.CompteRepository;
import com.snowden.core.model.Client;
import com.snowden.core.model.Compte;
import com.snowden.core.model.CompteCourant;
import com.snowden.core.service.BankService;
import com.snowden.core.service.ClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class CompteGestionApplication implements CommandLineRunner {

    private final ClientService clientService;
    private final BankService bankService;
    private final CompteRepository compteRepository;

    public CompteGestionApplication(ClientService clientService,
                                    BankService bankService,
                                    CompteRepository compteRepository){
        this.clientService = clientService;
        this.bankService = bankService;
        this.compteRepository = compteRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(CompteGestionApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Client client = new Client(1L, faker.name().username(), faker.internet().password());
        this.clientService.addClient(client);
        Compte compte = new CompteCourant("c1", new Date(), 12000, client, 6000);
        this.compteRepository.save(compte);
        this.bankService.verser("c1", 5000);
        this.bankService.retirer("c1", 2000);
    }
}
