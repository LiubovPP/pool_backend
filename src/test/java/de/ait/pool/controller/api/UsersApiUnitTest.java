package de.ait.pool.controller.api;
import de.ait.pool.controller.UsersController;
import de.ait.pool.dto.userDto.NewUserDto;
import de.ait.pool.dto.userDto.UpdateUserDto;
import de.ait.pool.dto.userDto.UserDto;
import de.ait.pool.models.User;
import de.ait.pool.security.details.AuthenticatedUser;
import de.ait.pool.service.UsersService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Users Controller Tests")
class UsersApiUnitTest {

    @Mock
    private UsersService usersService;

    @InjectMocks
    private UsersController usersController;


    @Test
    @DisplayName("Test registering a new user")
    void testRegisterUser() {
        // Arrange
        NewUserDto newUserDto = NewUserDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("Qwerty007!")
                .phoneNumber("+7 952 889 01 88")
                .build();

        UserDto userDto = UserDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .role("USER")
                .phoneNumber("+7 952 889 01 88")
                .build();

        // Мокируем метод register сервиса UsersService
        when(usersService.register(any(NewUserDto.class))).thenReturn(userDto);

        // Act
        UserDto result = usersController.register(newUserDto);

        // Assert
        assertNotNull(result);
        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getFirstName(), result.getFirstName());
        assertEquals(userDto.getLastName(), result.getLastName());
        assertEquals(userDto.getEmail(), result.getEmail());
        assertEquals(userDto.getRole(), result.getRole());
        assertEquals(userDto.getPhoneNumber(), result.getPhoneNumber());
    }

    @Test
    @DisplayName("Test getting all users")
    void testGetAllUsers() {
        // Arrange
        List<User> userList = new ArrayList<>();
        userList.add(User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .hashPassword("hashedPassword")
                .phoneNumber("+7 952 889 01 88")
                .role(User.Role.USER)
                .state(User.State.CONFIRMED)
                .build());
        userList.add(User.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .hashPassword("hashedPassword")
                .phoneNumber("+7 952 889 01 89")
                .role(User.Role.ADMIN)
                .state(User.State.CONFIRMED)
                .build());
        when(usersService.getAllUsers()).thenReturn(UserDto.from(userList));

        // Act
        List<UserDto> userDtoList = usersController.getAllUsers();

        // Assert
        assertNotNull(userDtoList);
        assertEquals(userList.size(), userDtoList.size());

        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            UserDto userDto = userDtoList.get(i);

            assertNotNull(userDto);
            assertEquals(user.getId(), userDto.getId());
            assertEquals(user.getFirstName(), userDto.getFirstName());
            assertEquals(user.getLastName(), userDto.getLastName());
            assertEquals(user.getEmail(), userDto.getEmail());
            assertEquals(user.getRole().toString(), userDto.getRole());
            assertEquals(user.getPhoneNumber(), userDto.getPhoneNumber());
        }
    }

    @Test
    @DisplayName("Test getting user profile")
    void testGetProfile() {
        // Arrange
        User user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .hashPassword("hashedPassword")
                .phoneNumber("+7 952 889 01 88")
                .role(User.Role.USER)
                .state(User.State.CONFIRMED)
                .build();
        AuthenticatedUser authenticatedUser = new AuthenticatedUser(user);
        when(usersService.getUserById(authenticatedUser.getId())).thenReturn(UserDto.from(user));

        // Act
        UserDto userDto = usersController.getProfile(authenticatedUser);

        // Assert
        assertNotNull(userDto);
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getRole().toString(), userDto.getRole());
        assertEquals(user.getPhoneNumber(), userDto.getPhoneNumber());
    }

    @Test
    @DisplayName("Test deleting a user")
    void testDeleteUser() {
        // Arrange
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .hashPassword("hashedPassword")
                .phoneNumber("+7 952 889 01 88")
                .role(User.Role.USER)
                .state(User.State.CONFIRMED)
                .build();
        when(usersService.findById(userId)).thenReturn(user);

        // Act
        UserDto userDto = usersController.deleteUser(userId);

        // Assert
        assertNotNull(userDto);
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getRole().toString(), userDto.getRole());
        assertEquals(user.getPhoneNumber(), userDto.getPhoneNumber());
        verify(usersService, times(1)).deleteUser(user);
    }

    @Test
    @DisplayName("Test updating a user")
    void testUpdateUser() {
        // Arrange
        Long userId = 1L;
        UpdateUserDto updateUserDto = UpdateUserDto.builder()
                .firstName("Johnny")
                .lastName("Doe")
                .role("USER")
                .phoneNumber("+7 952 889 01 88")
                .build();

        User user = User.builder()
                .id(userId)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .hashPassword("hashedPassword")
                .phoneNumber("+7 952 889 01 88")
                .role(User.Role.USER)
                .state(User.State.CONFIRMED)
                .build();

        User updatedUser = User.builder()
                .id(userId)
                .firstName(updateUserDto.getFirstName())
                .lastName(updateUserDto.getLastName())
                .email(user.getEmail())
                .hashPassword(user.getHashPassword())
                .phoneNumber(updateUserDto.getPhoneNumber())
                .role(User.Role.valueOf(updateUserDto.getRole()))
                .state(user.getState())
                .build();

        when(usersService.findById(userId)).thenReturn(user);
        when(usersService.updateUser(user, updateUserDto)).thenReturn(UserDto.from(updatedUser));

        // Act
        UserDto userDto = usersController.updateUser(userId, updateUserDto);

        // Assert
        assertNotNull(userDto);
        assertEquals(updatedUser.getId(), userDto.getId());
        assertEquals(updatedUser.getFirstName(), userDto.getFirstName());
        assertEquals(updatedUser.getLastName(), userDto.getLastName());
        assertEquals(updatedUser.getEmail(), userDto.getEmail());
        assertEquals(updatedUser.getRole().toString(), userDto.getRole());
        assertEquals(updatedUser.getPhoneNumber(), userDto.getPhoneNumber());
    }
}