package com.snowden;

import com.github.javafaker.Faker;
import com.snowden.core.dao.CompteRepository;
import com.snowden.core.model.Client;
import com.snowden.core.model.CompteCourant;
import com.snowden.core.model.CompteEpargne;
import com.snowden.core.model.Role;
import com.snowden.core.service.BankService;
import com.snowden.core.service.ClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class CompteGestionApplication implements CommandLineRunner {

    private final ClientService clientService;
    private final CompteRepository compteRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final BankService bankService;

    public CompteGestionApplication(ClientService clientService,
                                    CompteRepository compteRepository,
                                    BankService bankService,
                                    BCryptPasswordEncoder  bCryptPasswordEncoder){
        this.clientService = clientService;
        this.compteRepository = compteRepository;
        this.bankService = bankService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(CompteGestionApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        this.clientService.save(new Role("ROLE_USER"));
        this.clientService.save(new Role("ROLE_ADMIN"));
        Client client = this.clientService.addClient(new Client("farid", "chaibou", new Date(), "faridibnchouaib", this.bCryptPasswordEncoder.encode("12345")));
        this.compteRepository.save(new CompteCourant("code1", new Date(),  12580, client, 12300));
        this.compteRepository.save(new CompteEpargne("code2", new Date(),  12580, client, 2.5));
        this.bankService.verser("code1", 123600);
        this.bankService.retirer("code1", 1200);
        this.clientService.addRoleToClient("faridibnchouaib", "ROLE_USER");
        this.clientService.addRoleToClient("faridibnchouaib", "ROLE_ADMIN");

    }
}
