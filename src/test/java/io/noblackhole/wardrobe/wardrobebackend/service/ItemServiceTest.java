package io.noblackhole.wardrobe.wardrobebackend.service;

import io.noblackhole.wardrobe.wardrobebackend.TestItemCreationDto;
import io.noblackhole.wardrobe.wardrobebackend.TestItemDto;
import io.noblackhole.wardrobe.wardrobebackend.TestTag;
import io.noblackhole.wardrobe.wardrobebackend.domain.Category;
import io.noblackhole.wardrobe.wardrobebackend.domain.Item;
import io.noblackhole.wardrobe.wardrobebackend.domain.Tag;
import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.item.ItemCreationDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.item.ItemDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.DtoMapper;
import io.noblackhole.wardrobe.wardrobebackend.exception.item.ItemNotFoundException;
import io.noblackhole.wardrobe.wardrobebackend.exception.item.ItemServiceException;
import io.noblackhole.wardrobe.wardrobebackend.repository.ItemRepository;
import io.noblackhole.wardrobe.wardrobebackend.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

  @Mock
  ItemRepository itemRepository;
  @Mock
  TagRepository tagRepository;
  @Mock
  DtoMapper itemDtoMapper;

  @InjectMocks
  ItemServiceImpl itemService;

  User user1;
  Category category1;
  Item item1, item2;
  ItemDto itemDto1, itemDto2;

  @BeforeEach
  void setUp() {
    category1 = Category.SHOES;
    item1 = new Item.Builder().withId(11L)
      .withBrand("Hallmark Devilson")
      .withCategory(category1)
      .withColors(Set.of("Blue", "Red"))
      .build();
    item2 = new Item.Builder().withId(12L)
      .withBrand("Tristan's Fate")
      .withCategory(category1)
      .withColors(Set.of("Pink", "White"))
      .build();

    itemDto1 = new ItemDto.Builder().withId(11L)
      .withBrand("Hallmark Devilson")
      .withCategory(category1)
      .withColors(Set.of("Blue", "Red"))
      .build();
    itemDto2 = new ItemDto.Builder().withId(12L)
      .withBrand("Tristan's Fate")
      .withCategory(category1)
      .withColors(Set.of("Pink", "White"))
      .build();

    user1 = new User.Builder().withId(1L)
      .withFirstName("John")
      .withLastName("Doe")
      .withEmail("johndoe@gmail.com")
      .withPassword("password")
      .build();
  }

  @Test
  void findAllByUserId_whenNoItemsForUser_shouldReturnEmptyList() throws ItemServiceException {
    when(itemRepository.findAllByUserId(user1.getId())).thenReturn(Collections.emptyList());
    List<ItemDto> items = itemService.findAllByUserId(user1.getId());
    assertThat(items).isEmpty();
  }

  @Test
  void findAllByUserId_whenMultipleUsers_shouldReturnCorrectItems() throws ItemServiceException {
    when(itemRepository.findAllByUserId(user1.getId())).thenReturn(List.of(item1, item2));
    when(itemDtoMapper.itemToItemDto(item1)).thenReturn(itemDto1);
    when(itemDtoMapper.itemToItemDto(item2)).thenReturn(itemDto2);

    List<ItemDto> itemsForUser1 = itemService.findAllByUserId(user1.getId());
    assertThat(itemsForUser1).hasSize(2);
    assertThat(itemsForUser1).containsExactly(itemDto1, itemDto2);
  }

  @Test
  void findById() throws ItemServiceException, ItemNotFoundException {
    when(itemRepository.findById(item1.getId())).thenReturn(Optional.of(item1));
    when(itemDtoMapper.itemToItemDto(item1)).thenReturn(itemDto1);

    ItemDto actualItem = itemService.findById(item1.getId());
    assertThat(actualItem).isEqualTo(itemDto1);
  }

  @Test
  void save_validItem_shouldApply() throws ItemServiceException {
    ItemCreationDto itemCreationDto = TestItemCreationDto.create1();

    when(itemDtoMapper.itemCreationDtoToItem(itemCreationDto)).thenReturn(item1);
    when(itemRepository.save(item1)).thenReturn(item1);
    when(itemDtoMapper.itemToItemDto(item1)).thenReturn(itemDto1);

    ItemDto savedItemDto = itemService.save(itemCreationDto);
    assertThat(savedItemDto).isEqualTo(itemDto1);
    verify(itemRepository, times(1)).save(item1);
  }

  @Test
  void update_validItem_shouldApply() throws ItemServiceException {
    when(itemDtoMapper.itemDtoToItem(itemDto1)).thenReturn(item1);
    when(itemRepository.save(item1)).thenReturn(item1);
    when(itemDtoMapper.itemToItemDto(item1)).thenReturn(itemDto1);

    ItemDto updatedItemDto = itemService.update(itemDto1);
    assertThat(updatedItemDto).isEqualTo(itemDto1);
    verify(itemRepository, times(1)).save(item1);
  }

  @Test
  void delete_validId_shouldApply() throws ItemServiceException {
    Long id = 1L;
    doNothing().when(itemRepository)
      .deleteById(id);
    itemService.deleteById(id);
    verify(itemRepository, times(1)).deleteById(id);
  }

  @Test
  void findById_notFound_shouldThrowException() {
    Long itemId = 999L;
    when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

    assertThrows(ItemNotFoundException.class,
      () -> itemService.findById(itemId));
    verify(itemRepository, times(1)).findById(itemId);
  }

  @Test
  void save_withExistingTags_shouldReuseTags() throws ItemServiceException {
    Tag tag1 = TestTag.create1();
    Tag tag2 = TestTag.create2();
    ItemCreationDto itemCreationDto = TestItemCreationDto.create1();

    when(tagRepository.findByName(tag1.getName())).thenReturn(tag1);
    when(tagRepository.findByName(tag2.getName())).thenReturn(tag2);

    Item item = new Item();
    item.setTags(Set.of(tag1, tag2));
    when(itemDtoMapper.itemCreationDtoToItem(itemCreationDto)).thenReturn(item);
    when(itemRepository.save(item)).thenReturn(item);

    ItemDto itemDto = TestItemDto.create1();
    when(itemDtoMapper.itemToItemDto(item)).thenReturn(itemDto);

    ItemDto savedItemDto = itemService.save(itemCreationDto);

    assertThat(savedItemDto).isEqualTo(itemDto);
    verify(tagRepository, times(1)).findByName(tag1.getName());
    verify(tagRepository, times(1)).findByName(tag2.getName());
    verify(itemRepository, times(1)).save(item);
  }
}



























