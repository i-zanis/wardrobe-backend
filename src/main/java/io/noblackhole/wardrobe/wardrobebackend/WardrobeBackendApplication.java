package io.noblackhole.wardrobe.wardrobebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@ComponentScan({"io.noblackhole.wardrobe.wardrobebackend.domain.dto.item"})
@SpringBootApplication
public class WardrobeBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(WardrobeBackendApplication.class, args);
  }

}
