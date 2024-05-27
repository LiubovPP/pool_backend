package de.ait.pool.service;

import de.ait.pool.dto.userDto.NewUserDto;
import de.ait.pool.dto.userDto.UpdateUserDto;
import de.ait.pool.dto.userDto.UserDto;
import de.ait.pool.exceptions.RestException;
import de.ait.pool.models.User;
import de.ait.pool.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UsersServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsersService usersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test register method when user does not exist")
    void testRegisterUserDoesNotExist() {
        // Arrange
        NewUserDto newUserDto = new NewUserDto();
        newUserDto.setEmail("test@example.com");
        newUserDto.setFirstName("Test");
        newUserDto.setLastName("User");
        newUserDto.setPassword("password");
        newUserDto.setPhoneNumber("123456789");

        when(userRepository.existsByEmail(newUserDto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(newUserDto.getPassword())).thenReturn("encodedPassword");

        // Act
        UserDto registeredUser = usersService.register(newUserDto);

        // Assert
        assertNotNull(registeredUser);
        assertEquals("Test", registeredUser.getFirstName());
        assertEquals("User", registeredUser.getLastName());
        assertEquals("test@example.com", registeredUser.getEmail());
        assertEquals("USER", registeredUser.getRole());
        assertEquals("123456789", registeredUser.getPhoneNumber());
    }

    @Test
    @DisplayName("Test register method when user already exists")
    void testRegisterUserAlreadyExists() {
        // Arrange
        NewUserDto newUserDto = new NewUserDto();
        newUserDto.setEmail("test@example.com");
        newUserDto.setFirstName("Test");
        newUserDto.setLastName("User");
        newUserDto.setPassword("password");
        newUserDto.setPhoneNumber("123456789");

        when(userRepository.existsByEmail(newUserDto.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(RestException.class, () -> usersService.register(newUserDto));
    }

    @Test
    @DisplayName("Test getUserById method when user exists")
    void testGetUserByIdUserExists() {
        // Arrange
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phoneNumber("123456789")
                .role(User.Role.USER) // Убедитесь, что значение поля role инициализировано
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        UserDto userDto = usersService.getUserById(userId);

        // Assert
        assertNotNull(userDto);
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getPhoneNumber(), userDto.getPhoneNumber());
        assertEquals(user.getRole().toString(), userDto.getRole()); // Убедитесь, что значение поля role не null
    }

    @Test
    @DisplayName("Test getUserById method when user does not exist")
    void testGetUserByIdUserDoesNotExist() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> usersService.getUserById(userId));
    }

    @Test
    @DisplayName("Test findById method when user exists")
    void testFindByIdUserExists() {
        // Arrange
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phoneNumber("123456789")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        User foundUser = usersService.findById(userId);

        // Assert
        assertNotNull(foundUser);
        assertEquals(user, foundUser);
    }

    @Test
    @DisplayName("Test findById method when user does not exist")
    void testFindByIdUserDoesNotExist() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        User foundUser = usersService.findById(userId);

        // Assert
        assertNull(foundUser);
    }

    @Test
    @DisplayName("Test updateUser method")
    void testUpdateUser() {
        // Arrange
        Long userId = 1L;
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setFirstName("UpdatedFirstName");
        updateUserDto.setLastName("UpdatedLastName");
        updateUserDto.setPhoneNumber("987654321");
        updateUserDto.setRole("ADMIN");

        User userToUpdate = User.builder()
                .id(userId)
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("123456789")
                .role(User.Role.USER)
                .build();

        when(userRepository.save(userToUpdate)).thenReturn(userToUpdate);

        // Act
        UserDto updatedUserDto = usersService.updateUser(userToUpdate, updateUserDto);

        // Assert
        assertNotNull(updatedUserDto);
        assertEquals(userToUpdate.getId(), updatedUserDto.getId());
        assertEquals(updateUserDto.getFirstName(), updatedUserDto.getFirstName());
        assertEquals(updateUserDto.getLastName(), updatedUserDto.getLastName());
        assertEquals(updateUserDto.getPhoneNumber(), updatedUserDto.getPhoneNumber());
        assertEquals(updateUserDto.getRole(), updatedUserDto.getRole());
    }

    @Test
    @DisplayName("Test getAllUsers method")
    void testGetAllUsers() {
        // Arrange
        User user1 = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phoneNumber("123456789")
                .role(User.Role.USER)
                .build();

        User user2 = User.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@example.com")
                .phoneNumber("987654321")
                .role(User.Role.USER)
                .build();

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        when(userRepository.findAll()).thenReturn(userList);

        // Act
        List<UserDto> userDtoList = usersService.getAllUsers();

        // Assert
        assertNotNull(userDtoList);
        assertEquals(2, userDtoList.size());
    }

    @Test
    @DisplayName("Test deleteUser method")
    void testDeleteUser() {
        // Arrange
        Long userId = 1L;
        User userToDelete = User.builder()
                .id(userId)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phoneNumber("123456789")
                .role(User.Role.USER)
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(userToDelete));

        // Act
        UserDto deletedUserDto = usersService.deleteUser(userToDelete);

        // Assert
        assertNotNull(deletedUserDto);
        assertEquals(userToDelete.getId(), deletedUserDto.getId());
        assertEquals(userToDelete.getFirstName(), deletedUserDto.getFirstName());
        assertEquals(userToDelete.getLastName(), deletedUserDto.getLastName());
        assertEquals(userToDelete.getEmail(), deletedUserDto.getEmail());
        assertEquals(userToDelete.getPhoneNumber(), deletedUserDto.getPhoneNumber());
    }
}