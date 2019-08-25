package com.homecloud.backend.home_server.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity(name = "home_servers_connections")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HomeServerConnection {

    @Id
    @Column(name = "home_server_id")
    private int homeServerId;

    @Column(name = "last_address_update")
    private LocalDate lastAddressUpdate;

    @Column(name = "address")
    private String address;

}