package com.homecloud.backend.security;

import com.homecloud.backend.security.models.JwtTokenClaims;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SecurityMapper {

  @Mapping(target = "bluVolumePercent", source = "pcrBeerTypeVolume.volumePercent.bluPercent")
  JwtTokenClaims toPosDeliveredNewIndicationsStatusDto(PosDeliveredNewIndicationsStatus posDeliveredNewIndicationsStatus);
}
