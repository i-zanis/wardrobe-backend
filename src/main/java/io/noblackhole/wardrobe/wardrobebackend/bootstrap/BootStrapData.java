package io.noblackhole.wardrobe.wardrobebackend.bootstrap;

import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jt on 12/23/19.
 */
@Component
public class BootStrapData implements CommandLineRunner {

  UserRepository userRepository;

  public BootStrapData(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
    public void run(String... args) {
        System.out.println("Started in Bootstrap");
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("f@f");
        user.setPassword("f");
        userRepository.save(user);
        User user2 = new User();
        user2.setEmail("rwr");
        user2.setFirstName("rwr");
        user2.setLastName("wrw");
        userRepository.save(user2);
    System.out.println("REPO COUNT: " + userRepository.count());
    List<User> users = (List<User>) userRepository.findAll();
    System.out.println(users);
    }
}
