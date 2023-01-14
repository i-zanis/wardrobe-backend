package io.noblackhole.wardrobe.wardrobebackend.bootstrap;

import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.exception.UserNotFoundException;
import io.noblackhole.wardrobe.wardrobebackend.exception.UserServiceException;
import io.noblackhole.wardrobe.wardrobebackend.repository.UserRepository;
import io.noblackhole.wardrobe.wardrobebackend.service.UserService;
import io.noblackhole.wardrobe.wardrobebackend.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BootStrapData implements CommandLineRunner {
  private static final Logger logger = LoggerFactory.getLogger(BootStrapData.class);
  UserRepository userRepository;
  UserService userService;

  public BootStrapData(UserRepository userRepository, UserService userService) {
    this.userRepository = userRepository;
    this.userService = new UserServiceImpl(userRepository);

  }

  @Override
  public void run(String... args) throws UserServiceException, UserNotFoundException {
    logger.info("Loading bootstrap data");
    List<User> users = new ArrayList<>();
    try {
      users = userService.findAll();
    } catch (UserNotFoundException e) {
      logger.error("No users found in the DB");
    }
    if (users.isEmpty()) getUsers();
    ;
    logger.info("Bootstrap data loaded");
    logger.info("-------------------------------");

  }

  private void getUsers() throws UserServiceException, UserNotFoundException {
    User user1 = new User.Builder()
      .withId(1L)
      .withFirstName("John")
      .withLastName("Doe")
      .withEmail("johndoe@gmail.com")
      .withPassword("password")
      .build();

    User user2 = new User.Builder()
      .withId(2L)
      .withFirstName("Jane")
      .withLastName("Doe")
      .withEmail("janedoe@gmail.com")
      .withPassword("password")
      .build();

    User user3 = new User.Builder()
      .withFirstName("John")
      .withLastName("Smith")
      .withEmail("johnsmith@gmail.com")
      .withPassword("password")
      .build();

    User user4 = new User.Builder()
      .withFirstName("Jane")
      .withLastName("Smith")
      .withEmail("janesmith@gmail.com")
      .withPassword("password")
      .build();

    userService.save(user1);
    userService.save(user2);
    userService.save(user3);
    userService.save(user4);


    List<User> users = userService.findAll();
    logger.info("Users found: " + users.size());
    logger.info("-------------------------------");
    assert users.size() == 4;
    assert users.get(0)
      .getFirstName()
      .equals("John");
    assert users.get(0)
      .getLastName()
      .equals("Doe");
    assert users.get(0)
      .getEmail()
      .equals("johndoe@gmail.com");
    assert users.get(0)
      .getPassword()
      .equals("password");
  }
}
