package com.snowden.web.controller;

import com.snowden.core.model.Client;
import com.snowden.core.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    public String consulterClient(Model model,
                                  @RequestParam("id") Long id){
        try {
            Client client = this.clientService.consulterClient(id);
        }catch (Exception e){

        }
        return "";
    }
}
