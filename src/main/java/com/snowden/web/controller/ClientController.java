package com.snowden.web.controller;

import com.snowden.core.model.Client;
import com.snowden.core.model.Compte;
import com.snowden.core.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @GetMapping("/clients")
    public String client(){
        return "views/formClient";
    }

    @PostMapping("/consulterClient")
    public String consulterClient(Model model,
                                  @RequestParam("id") Long id,
                                  @RequestParam(defaultValue = "0", name = "page") int page,
                                  @RequestParam(defaultValue = "4", name = "size") int size){
        try {
            Client client = this.clientService.consulterClient(id);
            Page<Compte> comptes = this.clientService.listeCompte(id, page, size);
            model.addAttribute("client", client);
            model.addAttribute("page", comptes.getContent());
        }catch (Exception e){
            model.addAttribute("error",e);
        }
        return "views/formClient";
    }
}
