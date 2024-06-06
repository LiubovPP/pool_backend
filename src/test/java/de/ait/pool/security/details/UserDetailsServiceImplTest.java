package de.ait.pool.security.details;

import de.ait.pool.models.User;
import de.ait.pool.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test loadUserByUsername with existing user")
    void testLoadUserByUsernameWithExistingUser() {
        // Arrange
        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .hashPassword("hashedPassword")
                .role(User.Role.USER)
                .state(User.State.CONFIRMED)
                .build();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername("test@example.com");

        // Assert
        assertNotNull(userDetails);
        assertEquals("test@example.com", userDetails.getUsername());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    @DisplayName("Test loadUserByUsername with non-existing user")
    void testLoadUserByUsernameWithNonExistingUser() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("nonexisting@example.com");
        });
        verify(userRepository, times(1)).findByEmail("nonexisting@example.com");
    }
}