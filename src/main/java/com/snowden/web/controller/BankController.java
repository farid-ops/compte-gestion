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

    @GetMapping(value = "/operations")
    public String home(){
        return "views/compte";
    }

    @GetMapping(value = "/consulterCompte")
    public String consulterCompte(Model model,
                                  @RequestParam(name = "codeCompte1") String codeCompte,
                                  @RequestParam(defaultValue = "0", name = "page") int page,
                                  @RequestParam(defaultValue = "4",  name = "size") int size){
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
        return "views/compte";
    }

    @PostMapping(value = "/saveOperation")
    public String operations(Model model,
                             @RequestParam(name = "typeOperation") String typeOperation,
                             @RequestParam(name = "codeCompte1") String code1,
                             @RequestParam(name = "codeCompte2") String code2,
                             @RequestParam(name = "montant") double montant){
        try{
            if (typeOperation.equals("Versement"))
                this.bankService.verser(code1, montant);
            if (typeOperation.equals("Retrait"))
                this.bankService.retirer(code1, montant);
            if (typeOperation.equals("Virement"))
                this.bankService.virer(code1, code2, montant);
        }catch (Exception e){
            model.addAttribute("error",e);
            return "redirect:/consulter?codeCompte1="+code1+"&error="+e.getMessage();
        }
        return "redirect:/consulterCompte?codeCompte1="+code1;
    }
}
