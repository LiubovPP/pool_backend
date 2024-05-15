package de.ait.pool.controller;


import de.ait.pool.controller.api.UsersApi;
import de.ait.pool.dto.userDto.NewUserDto;
import de.ait.pool.dto.userDto.UpdateUserDto;
import de.ait.pool.dto.userDto.UserDto;
import de.ait.pool.exceptions.RestException;
import de.ait.pool.models.User;
import de.ait.pool.security.details.AuthenticatedUser;
import de.ait.pool.service.UsersService;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;


import java.util.List;

//@RequiredArgsConstructor
@RestController
public class UsersController implements UsersApi {

    private final UsersService usersService;

    // Конструктор, инъекция зависимости
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }


    @Override
    public UserDto register(NewUserDto newUser) {
        return usersService.register(newUser);
    }

//    @Override
//    public UserDto getConfirmation(String confirmCode) {
//        return usersService.confirm(confirmCode);
//    }

    @Override
    public UserDto getProfile(AuthenticatedUser user) {
        Long currentUserId = user.getId();
        return usersService.getUserById(currentUserId);
    }
    @Override
    public UserDto deleteUser(@PathVariable Long id) {
        User userToDelete = usersService.findById(id);
        if (userToDelete == null) {
            throw new RestException(HttpStatus.NOT_FOUND, "Пользователь с ID " + id + " не найден");
        }
        usersService.deleteUser(userToDelete);
        return UserDto.from(userToDelete);
    }


    @Transactional
    @Override
    public UserDto updateUser(Long id, UpdateUserDto updatedUser) {
        User userToUpdate = usersService.findById(id);

        if (userToUpdate == null) {
            throw new RestException(HttpStatus.NOT_FOUND, "Пользователь с ID " + id + " не найден");
        }

        return usersService.updateUser(userToUpdate, updatedUser);
    }


    @Override
    public List<UserDto> getAllUsers() {
        return usersService.getAllUsers();
    }
}