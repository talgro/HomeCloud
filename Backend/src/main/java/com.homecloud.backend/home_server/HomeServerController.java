package com.homecloud.backend.home_server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("home_servers")
public class HomeServerController {

    private HomeServerService homeServerService;

    @Autowired
    public HomeServerController(HomeServerService homeServerService) {
        this.homeServerService = homeServerService;
    }

    @RequestMapping(value = "updateAddress/{serverId}/{address}", method = RequestMethod.POST)
    public void updateAddress(@PathVariable("serverId") String serverId, @PathVariable("address") String address) {
        this.homeServerService.updateAddress(serverId, address);
    }

}