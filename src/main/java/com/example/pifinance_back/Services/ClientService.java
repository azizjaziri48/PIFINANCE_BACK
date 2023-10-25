package com.example.pifinance_back.Services;

import com.example.pifinance_back.Entities.Client;
import com.example.pifinance_back.Entities.Formation;

import java.util.List;

public interface ClientService {
    Client addClient(Client client);
    List<Client> retrieveAllClient();
    void removeClient(Long idClient );
    Client updateClient(Client client);
    Client retrieveClient(Long idClient);

    Client assignFormationToClient(Long idClient, int idFormation);
    Client unassignFormationFromClient(Long idClient, int idFormation);
}
