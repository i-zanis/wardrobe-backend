package io.noblackhole.wardrobe.wardrobebackend.repository;

import io.noblackhole.wardrobe.wardrobebackend.domain.Look;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LookRepository extends CrudRepository<Look, Long> {
  @Query("SELECT DISTINCT l FROM Look l JOIN l.items i WHERE i.user.id = " +
    ":userId")
  List<Look> findAllByUserId(@Param("userId") Long userId);

  @NonNull
  Optional<Look> findById(@NonNull Long id);
}