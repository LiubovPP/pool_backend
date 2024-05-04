package de.ait.pool.controller;


import de.ait.pool.controller.api.UsersApi;
import de.ait.pool.dto.NewUserDto;
import de.ait.pool.dto.UserDto;
import de.ait.pool.security.details.AuthenticatedUser;
import de.ait.pool.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;



@RequiredArgsConstructor
@RestController
public class UsersController implements UsersApi {

    private final UsersService usersService;


    @Override
    public UserDto register(NewUserDto newUser) {
        return usersService.register(newUser);
    }

    @Override
    public UserDto getProfile(AuthenticatedUser user) {
        Long currentUserId = user.getId();
        return usersService.getUserById(currentUserId);
    }


}
