package io.noblackhole.wardrobe.wardrobebackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.noblackhole.wardrobe.wardrobebackend.TestItem;
import io.noblackhole.wardrobe.wardrobebackend.domain.Item;
import io.noblackhole.wardrobe.wardrobebackend.exception.GlobalExceptionHandler;
import io.noblackhole.wardrobe.wardrobebackend.exception.ItemNotFoundException;
import io.noblackhole.wardrobe.wardrobebackend.exception.ItemServiceException;
import io.noblackhole.wardrobe.wardrobebackend.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {
  @Mock
  ItemService itemService;

  @InjectMocks
  ItemController itemController;
  MockMvc mockMvc;
  Item item;
  ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(itemController, GlobalExceptionHandler.class)
      .build();
    item = new Item();
    item.setId(1L);
  }

  @Test
  void findAll_validUserId_shouldReturn1UserItem() throws ItemServiceException {
    List<Item> items = Collections.singletonList(item);
    when(itemService.findAllByUserId(1L)).thenReturn(items);
    Iterable<Item> result = itemController.findAll(1L);
    assertThat(result).isEqualTo(items);
    verify(itemService, times(1)).findAllByUserId(1L);
  }

  @Test
  void findAll_validUserId_shouldReturn1UserItemAndOk() throws Exception {
    Item item = TestItem.create1();
    List<Item> items = Collections.singletonList(item);
    when(itemService.findAllByUserId(1L)).thenReturn(items);
    mockMvc.perform(get(ItemController.BASE_URL + "/all/" + item.getUser()
        .getId()))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].id", is(item.getId()
        .intValue())))
      .andExpect(jsonPath("$[0].brand", is(item.getBrand())))
      .andExpect(jsonPath("$[0].colors", hasSize(2)))
      .andExpect(jsonPath("$[0].price", is(item.getPrice())))
      .andExpect(jsonPath("$[0].material", is(item.getMaterial())))
      .andExpect(jsonPath("$[0].location", is(item.getLocation())))
      .andExpect(jsonPath("$[0].notes", is(item.getNotes())));
    verify(itemService, times(1)).findAllByUserId(1L);
  }

  @Test
  void findAll_validUserId_shouldReturnAllUserItems() throws ItemServiceException {
    List<Item> items = Arrays.asList(new Item(), new Item(), new Item());
    when(itemService.findAllByUserId(anyLong())).thenReturn(items);
    Iterable<Item> result = itemController.findAll(1L);
    assertThat(result).isEqualTo(items);
    verify(itemService, times(1)).findAllByUserId(1L);
  }

  @Test
  void findAll_validUserId_shouldReturnAllUserItemsAndOk() throws Exception {
    List<Item> items = Arrays.asList(new Item(), new Item(), new Item());
    when(itemService.findAllByUserId(anyLong())).thenReturn(items);
    String url = ItemController.BASE_URL + "/all/" + 1L;
    mockMvc.perform(get(url))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$", hasSize(3)));
    verify(itemService, times(1)).findAllByUserId(1L);
  }

  @Test
  void findAll_validUserId_shouldReturnEmptyList() throws Exception {
    when(itemService.findAllByUserId(1L)).thenReturn(Collections.emptyList());
    mockMvc.perform(get(ItemController.BASE_URL + "/all/" + 1L))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$", hasSize(0)));
    verify(itemService, times(1)).findAllByUserId(1L);
  }

  @Test
  void findById_validId_shouldReturnItem() throws ItemServiceException, ItemNotFoundException {
    when(itemService.findById(item.getId())).thenReturn(item);
    Item result = itemController.findById(item.getId());
    assertThat(result).isEqualTo(item);
    verify(itemService, times(1)).findById(item.getId());
  }

  @Test
  void findById_invalidId_shouldNotFound() throws Exception {
    when(itemService.findById(anyLong())).thenThrow(ItemNotFoundException.class);
    mockMvc.perform(get(ItemController.BASE_URL + "/" + 1L))
      .andDo(print())
      .andExpect(status().isNotFound());
    verify(itemService, times(1)).findById(1L);
  }

  @Test
  void findById_validId_shouldReturnItemAndOk() throws Exception {
    Item item = TestItem.create1();
    when(itemService.findById(item.getId())).thenReturn(item);
    mockMvc.perform(get(ItemController.BASE_URL + "/" + item.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id", is(item.getId()
        .intValue())))
      .andExpect(jsonPath("$.brand", is(item.getBrand())))
      .andExpect(jsonPath("$.colors", hasSize(2)))
      .andExpect(jsonPath("$.price", is(item.getPrice())))
      .andExpect(jsonPath("$.material", is(item.getMaterial())))
      .andExpect(jsonPath("$.location", is(item.getLocation())))
      .andExpect(jsonPath("$.notes", is(item.getNotes())));
    verify(itemService, times(1)).findById(item.getId());
  }

  @Test
  void delete_validId_shouldBeApplied() throws ItemServiceException {
    doNothing().when(itemService)
      .deleteById(1L);
    itemController.delete(item.getId());
    verify(itemService, times(1)).deleteById(item.getId());
  }

  @Test
  void delete_validId_shouldReturnNoContent() throws Exception {
    doNothing().when(itemService)
      .deleteById(1L);
    String url = ItemController.BASE_URL + "/1";
    mockMvc.perform(delete(url))
      .andExpect(status().isNoContent());
    verify(itemService, times(1)).deleteById(1L);
  }

  @Test
  void delete_invalidId_shouldThrowItemServiceException() throws Exception {
    doThrow(ItemServiceException.class).when(itemService).deleteById(anyLong());
    String url = ItemController.BASE_URL + "/" + anyLong();
    mockMvc.perform(delete(url))
      .andExpect(status().isBadRequest());
    verify(itemService, times(1)).deleteById(anyLong());
  }

  @Test
  void save_validItem_shouldBeApplied() throws ItemServiceException {
    when(itemService.save(item)).thenReturn(item);
    Item result = itemController.save(item);
    assertThat(result).isEqualTo(item);
    verify(itemService, times(1)).save(item);
  }

  @Test
  public void save_validId_shouldCreateItem() throws Exception {
    Item item = TestItem.create1();
    when(itemService.save(any())).thenReturn(item);
    mockMvc.perform(post(ItemController.BASE_URL).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(item)))
      .andDo(print())
      .andExpect(status().isCreated())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id", is(item.getId()
        .intValue())))
      .andExpect(jsonPath("$.brand", is(item.getBrand())))
      .andExpect(jsonPath("$.colors", hasSize(2)))
      .andExpect(jsonPath("$.price", is(item.getPrice())))
      .andExpect(jsonPath("$.material", is(item.getMaterial())))
      .andExpect(jsonPath("$.location", is(item.getLocation())))
      .andExpect(jsonPath("$.notes", is(item.getNotes())));
    verify(itemService, times(1)).save(any());
  }

  @Test
  void save_invalidId_shouldReturnBadRequestOnNoUser() throws Exception {
    Item item = new Item.Builder().withId(1L)
      .withUser(null)
      .build();
    when(itemService.save(any())).thenThrow(new ItemServiceException("No user"));
    mockMvc.perform(post(ItemController.BASE_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(item)))
      .andExpect(status().isBadRequest());
    verify(itemService, times(1)).save(any());
  }

  @Test
  public void update_validInput_shouldReturnNoContent() throws Exception {
    Item item = TestItem.create1();
    when(itemService.update(any())).thenReturn(item);
    String url = ItemController.BASE_URL + "/" + item.getId();
    mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(item)))
      .andDo(print())
      .andExpect(status().isNoContent());
    verify(itemService, times(1)).update(any());
  }

  @Test
  public void update_itemNotFound_shouldThrowItemServiceException() throws Exception {
    Item item = TestItem.create1();
    when(itemService.update(any())).thenThrow(ItemServiceException.class);
    String url = ItemController.BASE_URL + "/" + item.getId();
    String json = objectMapper.writeValueAsString(item);
    mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON)
        .content(json))
      .andExpect(status().isBadRequest());
    verify(itemService, times(1)).update(any());
    verify(itemService, times(0)).save(any());
  }


}