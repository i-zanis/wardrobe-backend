package io.noblackhole.wardrobe.wardrobebackend.domain.dto.look;

import io.noblackhole.wardrobe.wardrobebackend.domain.Look;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LookDtoMapper {
  Look lookDtoToLook(LookDto lookDto);

  LookDto lookToLookDto(Look look);

}
