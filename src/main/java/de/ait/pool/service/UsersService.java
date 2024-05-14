package de.ait.pool.service;


import de.ait.pool.dto.userDto.NewUserDto;
import de.ait.pool.dto.userDto.UpdateUserDto;
import de.ait.pool.dto.userDto.UserDto;
import de.ait.pool.exceptions.RestException;
import de.ait.pool.models.User;
import de.ait.pool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class UsersService {

    private final UserRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto register(NewUserDto newUser) {
        if (usersRepository.existsByEmail(newUser.getEmail())) {
            throw new RestException(HttpStatus.CONFLICT,
                    "Пользователь с email <" + newUser.getEmail() + "> уже существует");
        }

        User user = User.builder()
                .firstName(newUser.getFirstName())
                .lastName(newUser.getLastName())
                .email(newUser.getEmail())
                .hashPassword(passwordEncoder.encode(newUser.getPassword()))
                .role(User.Role.USER)
                .phoneNumber(newUser.getPhoneNumber())
                .build();

        usersRepository.save(user);

        return UserDto.from(user);
    }

    public UserDto getUserById(Long currentUserId) {
        return UserDto.from(usersRepository.findById(currentUserId).orElseThrow(() ->
                new EntityNotFoundException("Пользователь с ID " + currentUserId + " не найден")));
    }

    public User findById(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    public void deleteUser(User user) {
        usersRepository.delete(user);
    }
//TODO
   public UserDto updateUser(User userToUpdate, UpdateUserDto updatedUser) {
        userToUpdate.setFirstName(updatedUser.getFirstName());
        userToUpdate.setLastName(updatedUser.getLastName());
        userToUpdate.setPhoneNumber(updatedUser.getPhoneNumber());
        userToUpdate.setRole(User.Role.valueOf(updatedUser.getRole()));
        User savedUser = usersRepository.save(userToUpdate);
        return UserDto.from(savedUser);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = usersRepository.findAll();
        return users.stream().map(UserDto::from).collect(Collectors.toList());
    }
}

