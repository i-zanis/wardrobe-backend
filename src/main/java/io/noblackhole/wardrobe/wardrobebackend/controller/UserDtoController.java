package io.noblackhole.wardrobe.wardrobebackend.controller;

import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.UserDto;
import io.noblackhole.wardrobe.wardrobebackend.service.UserServiceDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


//@RestController
//@RequestMapping("/v1/users")        // more readable this way, that you put the common url behind brackets of RequestMapping!!! -> own opinion!
//public class UserDtoController {
//
//    private final UserServiceDto userServiceDto;
//
//    public UserDtoController(UserServiceDto userServiceDto) {
//        this.userServiceDto = userServiceDto;
//    }
//
//    // TODO created a new endpoint with UserDto (as record) + UserDtoMapper -> service function!
//    @GetMapping("/list/all")
//    public List<UserDto> findAllUsers() {
//        return userServiceDto.findAllUsers();
//    }
//}
