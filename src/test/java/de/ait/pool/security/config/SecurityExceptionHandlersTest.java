package de.ait.pool.security.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class SecurityExceptionHandlersTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private Authentication authentication;

    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        response = new MockHttpServletResponse();
    }

    @Test
    @DisplayName("Test AuthenticationEntryPoint")
    void testAuthenticationEntryPoint() throws Exception {
        // Arrange
        AuthenticationEntryPoint entryPoint = SecurityExceptionHandlers.ENTRY_POINT;

        // Act
        entryPoint.commence(request, response, null);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
        assertEquals("{\"message\":\"User unauthorized\"}", response.getContentAsString().trim());
    }

    @Test
    @DisplayName("Test AuthenticationSuccessHandler")
    void testAuthenticationSuccessHandler() throws Exception {
        // Arrange
        AuthenticationSuccessHandler successHandler = SecurityExceptionHandlers.LOGIN_SUCCESS_HANDLER;

        // Act
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
        assertEquals("{\"message\":\"Login successful\"}", response.getContentAsString().trim());
    }

    @Test
    @DisplayName("Test AuthenticationFailureHandler")
    void testAuthenticationFailureHandler() throws Exception {
        // Arrange
        AuthenticationFailureHandler failureHandler = SecurityExceptionHandlers.LOGIN_FAILURE_HANDLER;

        // Act
        failureHandler.onAuthenticationFailure(request, response, null);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
        assertEquals("{\"message\":\"Incorrect password or username\"}", response.getContentAsString().trim());
    }

    @Test
    @DisplayName("Test AccessDeniedHandler")
    void testAccessDeniedHandler() throws Exception {
        // Arrange
        when(authentication.getName()).thenReturn("test@example.com");
        when(authentication.getAuthorities()).thenReturn(Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AccessDeniedHandler accessDeniedHandler = SecurityExceptionHandlers.ACCESS_DENIED_HANDLER;

        // Act
        accessDeniedHandler.handle(request, response, null);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
        assertEquals("{\"message\":\"Access denied for user with email <test@example.com> and role []\"}", response.getContentAsString().trim());
    }

    @Test
    @DisplayName("Test LogoutSuccessHandler")
    void testLogoutSuccessHandler() throws Exception {
        // Arrange
        LogoutSuccessHandler logoutSuccessHandler = SecurityExceptionHandlers.LOGOUT_SUCCESS_HANDLER;

        // Act
        logoutSuccessHandler.onLogoutSuccess(request, response, authentication);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
        assertEquals("{\"message\":\"Logout successful\"}", response.getContentAsString().trim());
    }
}