package com.homecloud.backend.clients;

import com.homecloud.backend.clients.entities.HomeServerConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ClientsService {

    private ClientsRepository clientsRepository;

    @Autowired
    public ClientsService(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    public String getHomeServerAddress() {

        this.clientsRepository.;
    }

}
