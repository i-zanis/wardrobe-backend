package io.noblackhole.wardrobe.wardrobebackend.domain.dto.user;

import io.noblackhole.wardrobe.wardrobebackend.domain.Item;
import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.item.ItemCreationDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.item.ItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DtoMapper {
  @Mapping(target = "preferences", source = "preferences")
  UserDto userToUserDto(User user);

  @Mapping(target = "preferences", source = "preferences")
  User userDtoToUser(UserDto userDto);

  @Mapping(target = "items", source = "items")
  UserItemsDto userToUserItemsDto(User user);

  @Mapping(target = "items", source = "items")
  User userItemsDtoToUser(UserItemsDto userItemsDto);

  @Mapping(target = "password", ignore = true)
  User userCreationDtoToUser(UserCreationDto userCreationDto);

  //  @Mapping(target = "password", source = "user.password")
  UserCreationDto userToUserCreationDto(User user);


  // *************************************************************
  ItemCreationDto itemToItemCreationDto(Item item);

  @Mapping(source = "userId", target = "user.id")
//  @Mapping(target = "tags", source = "tags")
  Item itemCreationDtoToItem(ItemCreationDto itemCreationDto);

  @Mapping(source = "userId", target = "user.id")
  @Mapping(target = "tags", source = "tags")
  Item itemDtoToItem(ItemDto itemDto);

  @Mapping(source = "user.id", target = "userId")
  ItemDto itemToItemDto(Item item);
}