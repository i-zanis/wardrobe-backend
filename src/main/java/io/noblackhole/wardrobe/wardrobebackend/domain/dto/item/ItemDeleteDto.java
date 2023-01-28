package io.noblackhole.wardrobe.wardrobebackend.domain.dto.item;

import java.io.Serializable;

public record ItemDeleteDto(Long id, Long userId)
  implements Serializable {
}
