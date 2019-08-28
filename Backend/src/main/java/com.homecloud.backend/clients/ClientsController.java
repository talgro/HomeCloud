package com.homecloud.backend.clients;

import com.homecloud.backend.exceptions.ClientServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
public class ClientsController {

    private ClientsService clientsService;

    @Autowired
    public ClientsController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @RequestMapping(value = "getHomeServerAddress/{userId}", method = RequestMethod.GET)
    public String getHomeServerAddress(@PathVariable("userId") String userId) throws ClientServiceException {
        return this.clientsService.getHomeServerAddress(userId);
    }

}