package io.noblackhole.wardrobe.wardrobebackend.domain.dto;

import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.UserDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.UserItemsDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.UserPasswordDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserDtoMapper {

  @Mapping(target = "preferences", source = "user.preferences")
  UserDto userToUserDto(User user);

  @Mapping(target = "preferences", source = "userDto.preferences")
  User userDtoToUser(UserDto userDto);

  @Mapping(target = "items", source = "user.items")
  UserItemsDto userToUserItemsDto(User user);

  @Mapping(target = "items", source = "userItemsDto.items")
  User userItemsDtoToUser(UserItemsDto userItemsDto);

  @Mapping(target = "password", ignore = true)
  User userPasswordDtoToUser(UserPasswordDto userPasswordDto);

  @Mapping(target = "password", source = "user.password")
  UserPasswordDto userToUserPasswordDto(User user);
}