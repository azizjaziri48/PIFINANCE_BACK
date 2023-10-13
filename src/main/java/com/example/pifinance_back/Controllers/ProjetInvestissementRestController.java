package com.example.pifinance_back.Controllers;

import com.example.pifinance_back.Entities.PojetInvestissement;
import com.example.pifinance_back.Services.ProjetInvestissementService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/ProjetInvestissement")
public class ProjetInvestissementRestController {
    private ProjetInvestissementService projetInvestissementService;
    @GetMapping("/all")
    List<PojetInvestissement> retrieveAllPojetInvestissement() {
        return projetInvestissementService.retrieveAllPojetInvestissement();
    }
    @PostMapping("/add")
    PojetInvestissement AddPojetInvestissement(@RequestBody PojetInvestissement pojetInvestissement){
        return projetInvestissementService.addProjetInvestissement(pojetInvestissement);
    }
    @DeleteMapping("/delete/{id}")
    void removePojetInvestissement(@PathVariable("id") Integer idProjetInvestissement){
        projetInvestissementService.removePojetInvestissement(idProjetInvestissement);
    }
    @GetMapping("/get/{id}")
    PojetInvestissement retrievPojetInvestissement(@PathVariable("id") Integer idProjetInvestissement){
        return projetInvestissementService.retrievePojetInvestissement(idProjetInvestissement);
    }
    @PutMapping("/update")
    PojetInvestissement updatePojetInvestissement (@RequestBody PojetInvestissement pojetInvestissement){
        return projetInvestissementService.updatePojetInvestissement(pojetInvestissement);
    }

}
