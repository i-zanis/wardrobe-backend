package io.noblackhole.wardrobe.wardrobebackend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.item.ItemCreationDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.item.ItemDto;
import io.noblackhole.wardrobe.wardrobebackend.exception.GlobalExceptionHandler;
import io.noblackhole.wardrobe.wardrobebackend.exception.item.ItemNotFoundException;
import io.noblackhole.wardrobe.wardrobebackend.exception.item.ItemServiceException;
import io.noblackhole.wardrobe.wardrobebackend.mother.ItemCreationDtoMother;
import io.noblackhole.wardrobe.wardrobebackend.mother.ItemDtoMother;
import io.noblackhole.wardrobe.wardrobebackend.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ItemControllerTest {
  @Mock
  ItemService itemService;

  @InjectMocks
  ItemController itemController;
  MockMvc mockMvc;
  ItemDto itemDto;
  ItemCreationDto itemCreationDto;
  ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(itemController,
        GlobalExceptionHandler.class)
      .build();
    objectMapper = new ObjectMapper();
    itemDto = ItemDtoMother.create();
    itemCreationDto = ItemCreationDtoMother.create();
  }

  @Test
  void findAll_validUserId_shouldReturn1UserItem() throws ItemServiceException {
    List<ItemDto> itemDtos = Collections.singletonList(itemDto);
    when(itemService.findAllByUserId(1L)).thenReturn(itemDtos);
    Iterable<ItemDto> result = itemController.findAll(1L);
    assertThat(result).usingRecursiveComparison()
      .isEqualTo(itemDtos);
    verify(itemService, times(1)).findAllByUserId(1L);
  }

  @Test
  void findAll_validUserId_shouldReturn1UserItemAndOk() throws Exception {
    ItemDto itemDto = ItemDtoMother.create();
    List<ItemDto> items = Collections.singletonList(itemDto);
    when(itemService.findAllByUserId(1L)).thenReturn(items);

    MvcResult mvcResult = mockMvc.perform(get(ItemController.BASE_URL + "/all"
        + "/" + itemDto.userId()))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andReturn();

    String content = mvcResult.getResponse()
      .getContentAsString();
    List<ItemDto> actualItems = objectMapper.readValue(content,
      new TypeReference<>() {
      });

    assertThat(actualItems).usingRecursiveComparison()
      .isEqualTo(items);
    verify(itemService, times(1)).findAllByUserId(1L);
  }


  @Test
  void findAll_validUserId_shouldReturnAllUserItems() throws ItemServiceException {
    List<ItemDto> itemDtos = Arrays.asList(ItemDtoMother.create(),
      ItemDtoMother.create(), ItemDtoMother.create());
    when(itemService.findAllByUserId(anyLong())).thenReturn(itemDtos);
    Iterable<ItemDto> result = itemController.findAll(1L);
    assertThat(result).usingRecursiveComparison()
      .isEqualTo(itemDtos);
    verify(itemService, times(1)).findAllByUserId(1L);
  }

  @Test
  void findAll_validUserId_shouldReturnAllUserItemsAndOk() throws Exception {
    List<ItemDto> items = Arrays.asList(ItemDtoMother.create(),
      ItemDtoMother.create(), ItemDtoMother.create());
    when(itemService.findAllByUserId(anyLong())).thenReturn(items);
    String url = ItemController.BASE_URL + "/all/" + 1L;

    MvcResult mvcResult = mockMvc.perform(get(url))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andReturn();

    List<ItemDto> actualItems = objectMapper.readValue(mvcResult.getResponse()
      .getContentAsString(), new TypeReference<>() {
    });

    assertThat(actualItems).usingRecursiveComparison()
      .isEqualTo(items);

    verify(itemService, times(1)).findAllByUserId(1L);
  }

  @Test
  void findAll_validUserId_noItemsExist_shouldReturnEmptyListAndOk() throws Exception {
    when(itemService.findAllByUserId(1L)).thenReturn(Collections.emptyList());

    MvcResult mvcResult = mockMvc.perform(get(ItemController.BASE_URL + "/all" +
        "/" + 1L))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andReturn();

    String content = mvcResult.getResponse()
      .getContentAsString();
    List<ItemDto> actualItems = objectMapper.readValue(content,
      new TypeReference<>() {
    });

    assertThat(actualItems).isEmpty();

    verify(itemService, times(1)).findAllByUserId(1L);
  }

  @Test
  void findById_givenValidId_itemExists_shouldReturnItem() throws ItemServiceException {
    when(itemService.findById(itemDto.id())).thenReturn(itemDto);

    ItemDto result = itemController.findById(itemDto.id());

    assertThat(result).usingRecursiveComparison()
      .isEqualTo(itemDto);

    verify(itemService, times(1)).findById(itemDto.id());
  }

  @Test
  void findById_givenInvalidId_itemDoesNotExist_shouldReturnNotFound() throws Exception {
    when(itemService.findById(anyLong())).thenThrow(ItemNotFoundException.class);

    mockMvc.perform(get(ItemController.BASE_URL + "/" + 1L))
      .andExpect(status().isNotFound());

    verify(itemService, times(1)).findById(1L);
  }

  @Test
  void findById_givenValidId_itemExists_shouldReturnItemAndOk() throws Exception {
    ItemDto itemDto = ItemDtoMother.create();
    when(itemService.findById(itemDto.id())).thenReturn(itemDto);

    MvcResult mvcResult =
      mockMvc.perform(get(ItemController.BASE_URL + "/" + itemDto.id()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andReturn();

    String content = mvcResult.getResponse()
      .getContentAsString();
    ItemDto actualItem = objectMapper.readValue(content, ItemDto.class);

    assertThat(actualItem).usingRecursiveComparison()
      .isEqualTo(itemDto);

    verify(itemService, times(1)).findById(itemDto.id());
  }

  @Test
  void delete_givenValidId_itemExists_shouldDeleteItem() throws ItemServiceException {
    doNothing().when(itemService)
      .deleteById(itemDto.id());

    itemController.delete(itemDto.id());

    verify(itemService, times(1)).deleteById(itemDto.id());
  }

  @Test
  void delete_givenValidId_itemExists_shouldReturnNoContent() throws Exception {
    doNothing().when(itemService)
      .deleteById(1L);

    mockMvc.perform(delete(ItemController.BASE_URL + "/1"))
      .andExpect(status().isNoContent());

    verify(itemService, times(1)).deleteById(1L);
  }

  @Test
  void delete_givenInvalidId_itemDoesNotExist_shouldReturnBadRequest() throws Exception {
    doThrow(ItemServiceException.class).when(itemService)
      .deleteById(anyLong());

    mockMvc.perform(delete(ItemController.BASE_URL + "/99999"))
      .andExpect(status().isBadRequest());

    verify(itemService, times(1)).deleteById(99999L);
  }

  @Test
  void save_givenValidItem_shouldPersistItem() throws ItemServiceException {
    when(itemService.save(itemCreationDto)).thenReturn(itemDto);

    ItemDto result = itemController.save(itemCreationDto);

    assertThat(result).usingRecursiveComparison()
      .isEqualTo(itemDto);

    verify(itemService, times(1)).save(itemCreationDto);
  }

  @Test
  void save_givenValidItem_shouldCreateItemAndReturnCreated() throws Exception {
    ItemDto itemDto = ItemDtoMother.create();
    when(itemService.save(any())).thenReturn(itemDto);

    MvcResult mvcResult = mockMvc.perform(post(ItemController.BASE_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(itemCreationDto)))
      .andExpect(status().isCreated())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andReturn();

    String content = mvcResult.getResponse()
      .getContentAsString();
    ItemDto actualItem = objectMapper.readValue(content, ItemDto.class);

    assertThat(actualItem).usingRecursiveComparison()
      .isEqualTo(itemDto);

    verify(itemService, times(1)).save(any());
  }

  @Test
  void save_givenInvalidItem_shouldReturnBadRequest() throws Exception {
    when(itemService.save(any())).thenThrow(new ItemServiceException("No " +
      "user"));

    mockMvc.perform(post(ItemController.BASE_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(itemCreationDto)))
      .andExpect(status().isBadRequest());

    verify(itemService, times(1)).save(any());
  }

  @Test
  void update_givenValidInput_shouldReturnNoContent() throws Exception {
    when(itemService.update(any())).thenReturn(itemDto);

    mockMvc.perform(put(ItemController.BASE_URL + "/" + itemDto.id())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(itemDto)))
      .andExpect(status().isNoContent());

    verify(itemService, times(1)).update(any());
  }

  @Test
  void update_givenNonexistentItem_shouldReturnBadRequest() throws Exception {
    when(itemService.update(any())).thenThrow(ItemServiceException.class);

    mockMvc.perform(put(ItemController.BASE_URL + "/99999")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(itemDto)))
      .andExpect(status().isBadRequest());

    verify(itemService, times(1)).update(any());
    verify(itemService, times(0)).save(any());
  }
}