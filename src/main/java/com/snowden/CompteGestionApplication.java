package com.snowden;

import com.github.javafaker.Faker;
import com.snowden.core.model.Client;
import com.snowden.core.model.Role;
import com.snowden.core.service.ClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class CompteGestionApplication implements CommandLineRunner {

    private final ClientService clientService;

    public CompteGestionApplication(ClientService clientService){
        this.clientService = clientService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CompteGestionApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        this.clientService.save(new Role("ROLE_USER"));
        this.clientService.save(new Role("ROLE_ADMIN"));
        this.clientService.addClient(new Client("farid", "chaibou", new Date(), "faridibnchouaib", "12345"));
        this.clientService.addRoleToClient("faridibnchouaib", "ROLE_USER");
        this.clientService.addRoleToClient("faridibnchouaib", "ROLE_ADMIN");
        for (int i=0; i<20; i++){
            Client client = new Client(faker.name().firstName(), faker.name().lastName(), new Date(), faker.name().username(), faker.internet().password());
            this.clientService.addClient(client);
        }
    }
}
