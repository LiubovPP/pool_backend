package de.ait.pool.security.details;

import de.ait.pool.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class AuthenticatedUserTest {


    private User user;
    private AuthenticatedUser authenticatedUser;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("test@example.com")
                .hashPassword("hashedPassword")
                .role(User.Role.USER)
                .state(User.State.CONFIRMED)
                .build();
        authenticatedUser = new AuthenticatedUser(user);
    }

    @Test
    @DisplayName("Test enabled when user is confirmed")
    void testEnabledConfirmed() {
        assertTrue(authenticatedUser.isEnabled());
    }

    @Test
    @DisplayName("Test not enabled when user is not confirmed")
    void testNotEnabledNotConfirmed() {
        user.setState(User.State.NOT_CONFIRMED);
        authenticatedUser = new AuthenticatedUser(user);
        assertFalse(authenticatedUser.isEnabled());
    }

    @Test
    @DisplayName("Test not enabled when user is banned")
    void testNotEnabledBanned() {
        user.setState(User.State.BANNED);
        authenticatedUser = new AuthenticatedUser(user);
        assertFalse(authenticatedUser.isEnabled());
    }

    @Test
    @DisplayName("Test not enabled when user is deleted")
    void testNotEnabledDeleted() {
        user.setState(User.State.DELETED);
        authenticatedUser = new AuthenticatedUser(user);
        assertFalse(authenticatedUser.isEnabled());
    }

}