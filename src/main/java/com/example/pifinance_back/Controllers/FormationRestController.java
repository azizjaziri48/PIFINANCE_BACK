package com.example.pifinance_back.Controllers;

import com.example.pifinance_back.Entities.Formation;
import com.example.pifinance_back.Services.FormationService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/formation")
@CrossOrigin(origins = "http://localhost:4200")
public class FormationRestController {
    private FormationService formationService;

    @GetMapping("/all")
    List<Formation> retrieveAllFormation() {
        return formationService.retrieveAllFormation();
    }
    @PostMapping("/add")
    Formation AddFormation(@RequestBody Formation formation){
        return formationService.addFormation(formation);
    }
    @DeleteMapping("/delete/{id}")
    void removeFormation (@PathVariable("id") Integer idFormation){
        formationService.removeFormation(idFormation);
    }
    @GetMapping("/get/{id}")
    Formation retrieveFormation (@PathVariable("id") Integer idFormation){
        return formationService.retrieveFormation(idFormation);
    }
    @PutMapping("/update")
    Formation updateFormation (@RequestBody Formation formation){
        return formationService.updateFormation(formation);
    }
    @GetMapping("/betweendate/{date_debut}/{date_fin}")
    List<Formation> retrieveFormationdatebetween(@PathVariable("date_debut") @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate datedebut, @PathVariable("date_fin") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate datefin ){
        return formationService.findByDateBetween(datedebut,datefin);
    }
    @GetMapping("/orderbydate")
    List<Formation> retrieveFormationbydate(){
        return formationService.findByOrderByDate_debutAsc();
    }
}
