package com.snowden.web.controller;

import com.snowden.core.model.Compte;
import com.snowden.core.model.Operation;
import com.snowden.core.service.BankService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService){
        this.bankService = bankService;
    }

    @GetMapping(value = "/operation")
    public String home(){
        return "compte";
    }

    @PostMapping(value = "/consulter")
    public String consulterCompte(Model model,
                                  @RequestParam("codeCompte") String codeCompte,
                                  @RequestParam("page") int page,
                                  @RequestParam("size") int size){
        try {
            Compte compte = this.bankService.consulterCompte(codeCompte);
            Page<Operation> operations = this.bankService.listeOperation(codeCompte, page, size);
            model.addAttribute("codeCompte", codeCompte);
            model.addAttribute("compte", compte);
            model.addAttribute("pageCourant", page);
            model.addAttribute("operations", operations.getContent());
        }catch (Exception e){
            model.addAttribute("error", e);
        }
        return "compte";
    }

    @PostMapping(value = "/operation")
    public String operations(Model model,
                             @RequestParam("operation") String typeOperation,
                             @RequestParam("code1") String code1,
                             @RequestParam("code2") String code2,
                             @RequestParam("montant") double montant,
                             @RequestParam("page") int page,
                             @RequestParam("size") int size){
        try{
            if (typeOperation.equals("VERSER"))
                this.bankService.verser(code1, montant);
            if (typeOperation.equals("RETRAIT"))
                this.bankService.retirer(code1, montant);
            if (typeOperation.equals("VIRER"))
                this.bankService.virer(code1, code2, montant);
        }catch (Exception e){
            model.addAttribute("error",e);
            return "redirect:/consulter?code1="+code1+"&error="+e.getMessage();
        }
        return "redirecte:/consulter?code1="+code1;
    }
}
