package com.homecloud.backend.clients.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "server_details")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServerDetails {

    @Id
    @Column(name = "server_id")
    private String serverId;

    @Column(name = "server_name")
    private String serverName;

    @Column(name = "server_address")
    private String serverAddress;

}