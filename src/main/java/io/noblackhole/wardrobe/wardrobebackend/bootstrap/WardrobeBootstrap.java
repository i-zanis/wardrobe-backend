package io.noblackhole.wardrobe.wardrobebackend.bootstrap;

import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("default")
public class WardrobeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  private static final Logger logger = LoggerFactory.getLogger(WardrobeBootstrap.class);
  private final UserRepository userRepository;

  public WardrobeBootstrap(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  @Override
  @Transactional
  public void onApplicationEvent(ContextRefreshedEvent event) {
    System.out.println(userRepository.saveAll(getData()));
    logger.info("Loading Bootstrap Data");
  }

  private Iterable<User> getData() {
    List<User> users = new ArrayList<>(2);

    User user1 = new User();
    user1.setFirstName("John");
    user1.setLastName("Doe");
    user1.setEmail("john@gmail.com");
    user1.setPassword("password");
    users.add(user1);
    return users;
  }
}
