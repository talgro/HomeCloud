package com.homecloud.backend.clients;

import com.homecloud.backend.clients.DTOs.ServerUsersDto;
import com.homecloud.backend.clients.entities.ServerUsers;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ServerUsersDto toUsersServersDto(ServerUsers usersServers);
}
