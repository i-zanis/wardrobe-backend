package io.noblackhole.wardrobe.wardrobebackend.controller;

import io.noblackhole.wardrobe.wardrobebackend.domain.dto.look.LookDto;
import io.noblackhole.wardrobe.wardrobebackend.exception.item.ItemNotFoundException;
import io.noblackhole.wardrobe.wardrobebackend.exception.look.LookServiceException;
import io.noblackhole.wardrobe.wardrobebackend.service.LookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(LookController.BASE_URL)
public class LookController {
  public static final String BASE_URL = "/v1/looks";
  private static final Logger logger =
    LoggerFactory.getLogger(LookController.class);
  private final LookService lookService;

  public LookController(LookService lookService) {
    this.lookService = lookService;
  }

  @GetMapping("/all/{userId}")
  @ResponseStatus(HttpStatus.OK)
  public List<LookDto> findAllByUserId(@PathVariable Long userId) throws LookServiceException {
    logger.info("Received request to get all looks for user ID: {}", userId);
    return lookService.findAllByUserId(userId);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public LookDto findById(@PathVariable Long id) throws LookServiceException {
    logger.info("Received request to get look with ID: {}", id);
    return lookService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public LookDto save(@RequestBody LookDto lookDto) throws LookServiceException {
    logger.info(lookDto.toString());
    logger.info("Received request to save look: {}", lookDto);
    return lookService.save(lookDto);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public LookDto update(@PathVariable Long id, @RequestBody LookDto lookDto) throws LookServiceException, ItemNotFoundException {
    logger.info("Received request to update look with ID: {}", id);
    return lookService.update(lookDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable Long id) throws LookServiceException {
    logger.info("Received request to delete look with ID: {}", id);
    lookService.deleteById(id);
  }
}
