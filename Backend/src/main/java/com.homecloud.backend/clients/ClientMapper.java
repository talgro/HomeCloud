package com.homecloud.backend.clients;

import com.homecloud.backend.clients.DTOs.UsersServersDto;
import com.homecloud.backend.clients.entities.UsersServers;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    UsersServersDto toUsersServersDto(UsersServers usersServers);
}
