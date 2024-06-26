package de.ait.pool.controller.api;

import de.ait.pool.dto.userDto.NewUserDto;
import de.ait.pool.dto.StandardResponseDto;
import de.ait.pool.dto.userDto.UpdateUserDto;
import de.ait.pool.dto.userDto.UserDto;
import de.ait.pool.security.details.AuthenticatedUser;
import de.ait.pool.validation.dto.ValidationErrorsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Tags(
        @Tag(name = "Users")
)
@RequestMapping("/api/users")
public interface UsersApi {

    @Operation(summary = "Регистрация пользователя", description = "Доступно всем. По умолчанию роль - USER")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Пользователь зарегистрирован",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Ошибка валидации",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorsDto.class))),
            @ApiResponse(responseCode = "409",
                    description = "Пользователь с таким email уже есть",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class))),
    })
    // регистрация пользователя
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    UserDto register(@RequestBody @Valid NewUserDto newUser);

    @GetMapping("/confirm/{confirm-code}")
    UserDto getConfirmation(@PathVariable("confirm-code") String confirmCode);

    // получение текущего (своего) профиля
    @GetMapping("/profile")
    @Operation(summary = "профиль пользователя", description = "Доступно аутентифицированному пользователю. По умолчанию роль - ADMIN, USER")
    UserDto getProfile(@Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser user);

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя", description = "Доступно администратору. По умолчанию роль - ADMIN")
    UserDto deleteUser (@PathVariable Long id);

    @Operation(summary = "Обновить пользователя", description = "Доступно администратору. По умолчанию роль - ADMIN")
    @PutMapping("/{id}")
    UserDto updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserDto updatedUser);

    @GetMapping
    @Operation(summary = "Список пользователей", description = "Доступно администратору. По умолчанию роль - ADMIN")
   List<UserDto> getAllUsers();
    }

