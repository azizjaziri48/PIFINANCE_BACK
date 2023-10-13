package com.example.pifinance_back.Services;

import com.example.pifinance_back.Entities.PojetInvestissement;

import java.util.List;

public interface ProjetInvestissementService {
    PojetInvestissement addProjetInvestissement(PojetInvestissement pojetInvestissement);
    List<PojetInvestissement> retrieveAllPojetInvestissement();
    void removePojetInvestissement(int idProjetInvestissement);
    PojetInvestissement updatePojetInvestissement(PojetInvestissement pojetInvestissement);
    PojetInvestissement retrievePojetInvestissement(int idProjetInvestissement);
}
