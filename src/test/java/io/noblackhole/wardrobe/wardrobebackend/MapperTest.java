package io.noblackhole.wardrobe.wardrobebackend;

import io.noblackhole.wardrobe.wardrobebackend.domain.Category;
import io.noblackhole.wardrobe.wardrobebackend.domain.Item;

import java.util.Arrays;
import java.util.HashSet;

class MapperTest {

//  @Test
//  void mapItemEntityToItemDto_shouldReturnDto_whenAnEntityIsProvided() {
//    // Given
//    Item item = createTestItem();
//    // item.getColors().forEach(System.out::println);
//    ItemDto expectedDto = new ItemDto(new HashSet<>(Arrays.asList(Color.MAGENTA, Color.WHITE)), "Nike",
//      Category.TOP, null, true, TestUser.create1(), 10.59, null, "cotton",
//      "location", "care", "note");
//
//    // When
//    ItemDto itemDto = io.noblackhole.wardrobe.wardrobebackend.util.Mapper.mapItemEntityToItemDto(item);
//
//    // Then
//    then(itemDto)
//      .usingRecursiveComparison()
//      .isEqualTo(expectedDto);
//  }

  private Item createTestItem() {
    Item item = new Item();
    item.setColors(new HashSet<>(Arrays.asList("BLUE", "RED")));
    item.setBrand("Nike");
    item.setCategory(Category.TOP);
    item.setLooks(new HashSet<>());
    item.setFavorite(true);
    item.setUser(TestUser.createUser1());
    item.setPrice(10.59);
    item.setNotes("note");
    return item;
  }
}