package io.noblackhole.wardrobe.wardrobebackend.service;

import io.noblackhole.wardrobe.wardrobebackend.domain.dto.look.LookDto;
import io.noblackhole.wardrobe.wardrobebackend.exception.look.LookServiceException;

import java.util.List;

public interface LookService {
  List<LookDto> findAllByUserId(Long userId) throws LookServiceException;

  LookDto findById(Long id) throws LookServiceException;

  LookDto save(LookDto lookDto) throws LookServiceException;

  LookDto update(LookDto lookDto) throws LookServiceException;

  void deleteById(Long id) throws LookServiceException;
}
