package de.ait.pool.service;


import de.ait.pool.dto.userDto.NewUserDto;
import de.ait.pool.dto.userDto.UserDto;
import de.ait.pool.exceptions.RestException;
import de.ait.pool.models.User;
import de.ait.pool.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static de.ait.pool.dto.userDto.UserDto.from;


@RequiredArgsConstructor
@Service
public class UsersService {

    private final UserRepository usersRepository;

    private final PasswordEncoder passwordEncoder;


    public UserDto register(NewUserDto newUser) {

        if (usersRepository.existsByEmail(newUser.getEmail())) {
            throw new RestException(HttpStatus.CONFLICT,
                    "User with email <" + newUser.getEmail() + "> already exists");
        }

        User user = User.builder()
                .firstName(newUser.getFirstName())
                .lastName(newUser.getLastName())
                .email(newUser.getEmail())
                .hashPassword(passwordEncoder.encode(newUser.getPassword()))
                .role(User.Role.USER)
                .build();

        usersRepository.save(user);

        return from(user);
    }


    public UserDto getUserById(Long currentUserId) {
        return from(usersRepository.findById(currentUserId).orElseThrow());
    }


}
