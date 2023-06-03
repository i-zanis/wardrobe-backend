package io.noblackhole.wardrobe.wardrobebackend.mother;

import io.noblackhole.wardrobe.wardrobebackend.mother.UserMother;
import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.UserCreationDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.UserDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.UserDtoBase;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.DtoMapper;
import io.noblackhole.wardrobe.wardrobebackend.exception.user.UserNotFoundException;
import io.noblackhole.wardrobe.wardrobebackend.exception.user.UserServiceException;
import io.noblackhole.wardrobe.wardrobebackend.repository.UserRepository;
import io.noblackhole.wardrobe.wardrobebackend.service.UserService;
import io.noblackhole.wardrobe.wardrobebackend.service.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  UserRepository userRepository;
  UserService userService;
  @Mock
  DtoMapper userDtoMapper;

  @BeforeEach
  void setUp() {
    userDtoMapper = mock(DtoMapper.class);
    userService = new UserServiceImpl(userRepository, userDtoMapper);
  }

  @AfterEach
  void tearDown() {
  }


  @Test
  void findById_shouldReturnAUser_whenValidIdIsProvided() throws UserServiceException, UserNotFoundException {
    // Given
    User expected = UserMother.createUser();
    UserDto expectedDto = UserMother.createUserDto1();
    when(userRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
    when(userDtoMapper.userToUserDto(expected)).thenReturn(expectedDto);
    // When
    UserDtoBase actual = userService.findById(expected.getId(), null);
    // Then
    assertEquals(expectedDto, actual);
    verify(userRepository).findById(expected.getId());
    verify(userDtoMapper).userToUserDto(expected);
  }

  @Test
  void findById_shouldThrowUserServiceException_whenInvalidIdIsProvided() throws UserNotFoundException, UserServiceException {
    // Given
    Long nonExistentId = -1L;
    User expected = UserMother.createUser();
    Long existentId = expected.getId();
    UserDto expectedDto = UserMother.createUserDto1();
    when(userRepository.findById(existentId)).thenReturn(Optional.of(expected));
    when(userDtoMapper.userToUserDto(expected)).thenReturn(expectedDto);
    // When
    UserDtoBase userDtoBase = userService.findById(existentId, null);
    assertNotNull(userDtoBase);
    assertThrows(UserNotFoundException.class, () -> userService.findById(nonExistentId, null));
    // Then
    verify(userRepository, times(1)).findById(existentId);
    verify(userRepository, times(1)).findById(nonExistentId);
    verify(userDtoMapper, times(1)).userToUserDto(expected);
  }

  @Test
  void save_shouldReturnSavedUser_whenValidUserIsProvided() throws UserServiceException, UserNotFoundException {
    // Given
    UserCreationDto userCreationDto = new UserCreationDto("John", "Doe", "johndoe@gmail.com", "john-pass123");
    User expected = UserMother.createUser();
    when(userDtoMapper.userCreationDtoToUser(any())).thenReturn(expected);
    when(userRepository.save(expected)).thenReturn(expected);
    when(userRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
    // When
    userService.save(userCreationDto);
    UserDtoBase actual = userService.findById(expected.getId(), null);
    // Then
    assertEquals(userDtoMapper.userToUserDto(expected), actual);
  }




  @Test
  public void testSave_WhenUserIsNull_ShouldThrowUserServiceException() {
    assertThrows(UserServiceException.class, () -> userService.save(null));
  }


  @Test
  public void save_WhenSaveFails_ShouldThrowUserServiceException() {
    when(userRepository.save(any(User.class))).thenThrow(new DataAccessException("Error saving user") {
    });
    when(userDtoMapper.userCreationDtoToUser(any(UserCreationDto.class))).thenReturn(UserMother.createUser());
    assertThrows(UserServiceException.class, () -> userService.save(UserMother.createCreationDto1()));
  }

  @Test
  void update_shouldReturnUpdatedUser_whenValidUserIsProvided() throws UserServiceException {
    // Given
    UserDto userDto = UserMother.createUserDto1();
    User expected = UserMother.createUser();
    when(userDtoMapper.userDtoToUser(userDto)).thenReturn(expected);
    when(userRepository.save(expected)).thenReturn(expected);
    when(userDtoMapper.userToUserDto(expected)).thenReturn(userDto);
    // When
    UserDto actual = userService.update(userDto);

    // Then
    assertEquals(userDto, actual);
    verify(userDtoMapper).userDtoToUser(userDto);
    verify(userRepository).save(expected);
    verify(userDtoMapper).userToUserDto(expected);
  }

  @Test
  void update_shouldThrowUserServiceException_whenNullUserIsProvided() {
    // Given
    UserDto userDto = null;
    // When
    assertThrows(UserServiceException.class, () -> userService.update(userDto));
    // Then
    verify(userDtoMapper, times(0)).userDtoToUser(userDto);
    verify(userRepository, times(0)).save(any());
    verify(userDtoMapper, times(0)).userToUserDto(any());
  }


  @Test
  void login() {
  }

  @Test
  void isAuthorized() {
  }

  @Test
  void register() {
  }

  @Test
  void isValid() {
  }
}
