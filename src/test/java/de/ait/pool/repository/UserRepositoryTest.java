package de.ait.pool.repository;

import de.ait.pool.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserRepositoryTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserRepositoryTest userRepositoryTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Проверка метода findByEmail возвращает пользователя, если email существует")
    void findByEmail_ReturnsUser_WhenEmailExists() {
        // Arrange
        User user = User.builder()
                .id(1L)
                .firstName("Max")
                .lastName("Mustermann")
                .email("max@example.com")
                .hashPassword("password")
                .phoneNumber("123456789")
                .role(User.Role.USER)
                .state(User.State.CONFIRMED)
                .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        // Act
        Optional<User> foundUser = userRepository.findByEmail("max@example.com");

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(user.getId(), foundUser.get().getId());
        assertEquals(user.getEmail(), foundUser.get().getEmail());
    }

    @Test
    @DisplayName("Проверка метода findByEmail возвращает пустой Optional, если email не существует")
    void findByEmail_ReturnsEmptyOptional_WhenEmailDoesNotExist() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act
        Optional<User> foundUser = userRepository.findByEmail("unknown@example.com");

        // Assert
        assertFalse(foundUser.isPresent());
    }

    @Test
    @DisplayName("Проверка метода existsByEmail возвращает true, если email существует")
    void existsByEmail_ReturnsTrue_WhenEmailExists() {
        // Arrange
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // Act
        boolean exists = userRepository.existsByEmail("max@example.com");

        // Assert
        assertTrue(exists);
    }

    @Test
    @DisplayName("Проверка метода existsByEmail возвращает false, если email не существует")
    void existsByEmail_ReturnsFalse_WhenEmailDoesNotExist() {
        // Arrange
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        // Act
        boolean exists = userRepository.existsByEmail("unknown@example.com");

        // Assert
        assertFalse(exists);
    }


}