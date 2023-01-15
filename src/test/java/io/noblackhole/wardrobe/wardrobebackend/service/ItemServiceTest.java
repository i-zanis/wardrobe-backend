package io.noblackhole.wardrobe.wardrobebackend.service;

import io.noblackhole.wardrobe.wardrobebackend.domain.Category;
import io.noblackhole.wardrobe.wardrobebackend.domain.Color;
import io.noblackhole.wardrobe.wardrobebackend.domain.Item;
import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.exception.ItemServiceException;
import io.noblackhole.wardrobe.wardrobebackend.repository.ItemRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {
  @Mock
  ItemRepository itemRepository;
  @InjectMocks
  ItemServiceImpl itemService;
  User user1;
  Category category1, category2;
  Item item1, item2;

  @BeforeEach
  void setUp() {
    category1 = new Category(1L, "Footwear");
    item1 = new Item.Builder().withId(11L)
      .withBrand("Hallmark Devilson")
      .withCategory(category1)
      .withColors(Set.of(Color.BLUE, Color.RED))
      .withUser(user1)
      .build();
    item2 = new Item.Builder().withId(12L)
      .withBrand("Tristan's Fate")
      .withCategory(category1)
      .withColors(Set.of(Color.PINK, Color.WHITE))
      .withUser(user1)
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
    List<Item> items = itemService.findAllByUserId(user1.getId());
    assertThat(items).isEmpty();
  }

  @Test
  void findAllByUserId_whenMultipleUsers_shouldReturnCorrectItems() throws ItemServiceException {
    User user2 = new User.Builder().withId(2L)
      .build();
    Item item3 = new Item.Builder().withId(13L)
      .withUser(user2)
      .build();
    Item item4 = new Item.Builder().withId(14L)
      .withUser(user2)
      .build();

    when(itemRepository.findAllByUserId(user1.getId())).thenReturn(List.of(item1, item2));
    when(itemRepository.findAllByUserId(user2.getId())).thenReturn(List.of(item3, item4));

    List<Item> itemsForUser1 = itemService.findAllByUserId(user1.getId());
    assertThat(itemsForUser1).hasSize(2);
    assertThat(itemsForUser1).containsExactly(item1, item2);

    List<Item> itemsForUser2 = itemService.findAllByUserId(user2.getId());
    assertThat(itemsForUser2).hasSize(2);
    assertThat(itemsForUser2).containsExactly(item3, item4);
  }

  @Test
  void findAllByUserId() throws ItemServiceException {
    User user = user1;
    Item personalItem1 = item1, personalItem2 = item2;
    personalItem1.setUser(user);
    personalItem2.setUser(user);
    List<Item> personalItems = List.of(personalItem1, personalItem2);
    when(itemRepository.findAllByUserId(user.getId())).thenReturn(personalItems);
    List<Item> items = itemService.findAllByUserId(user.getId());
    assertThat(items).hasSize(2);
  }

  @Test
  void findById() throws ItemServiceException {
    User user = user1;
    Item item = item1;
    user.addItem(item);
    item.setUser(user);
    when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));
    Item actualItem = itemService.findById(item.getId());
    assertThat(user.getId()).isNotEqualTo(actualItem.getId());
    assertThat(actualItem.getBrand()).isEqualTo(item.getBrand());
    assertThat(actualItem.getNotes()).isEqualTo(item.getNotes());
    assertThat(actualItem.getCategory()).isEqualTo(item.getCategory());
    assertThat(actualItem.getColors()).isEqualTo(item.getColors());
    assertThat(actualItem.getBrand()).isEqualTo(item.getBrand());
    assertThat(actualItem.getUser()).isEqualTo(item.getUser());
  }

  @Test
  void testSave() throws ItemServiceException {
    Item item = item1;
    item.setNotes("New Item");
    when(itemRepository.save(item)).thenReturn(item);
    itemService.save(item);
    verify(itemRepository, times(1)).save(item);
  }

  @Test
  void testUpdate() throws ItemServiceException {
    Item item = item1;
    when(itemRepository.save(item)).thenReturn(item);
    item.setNotes("New Item");
    itemService.update(item);
    verify(itemRepository, times(1)).save(item);
  }

  @Test
  void deleteById() throws ItemServiceException {
    Long id = 1L;
    doNothing().when(itemRepository)
      .deleteById(id);
    itemService.deleteById(id);
    verify(itemRepository, times(1)).deleteById(id);
  }
}