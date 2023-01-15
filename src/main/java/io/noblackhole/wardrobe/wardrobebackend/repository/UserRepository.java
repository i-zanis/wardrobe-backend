package io.noblackhole.wardrobe.wardrobebackend.repository;

import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

  @NotNull List<User> findAll();

  Optional<User> findByEmail(String email);

  @NonNull
  Optional<User> findById(@NonNull Long id);

}
