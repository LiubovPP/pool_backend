package de.ait.pool.service;


import de.ait.pool.dto.userDto.NewUserDto;
import de.ait.pool.dto.userDto.UpdateUserDto;
import de.ait.pool.dto.userDto.UserDto;
import de.ait.pool.exceptions.RestException;
//import de.ait.pool.mail.MailTemplatesUtil;
//import de.ait.pool.mail.PoolProjectMailSender;
//import de.ait.pool.models.ConfirmationCode;
import de.ait.pool.models.User;
//import de.ait.pool.repository.ConfirmationCodesRepository;
import de.ait.pool.models.cart.Cart;
import de.ait.pool.repository.CartRepository;
import de.ait.pool.repository.UserRepository;
//import freemarker.template.Configuration;
import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static de.ait.pool.dto.userDto.UserDto.from;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class UsersService {

    private final UserRepository usersRepository;

    // private final ConfirmationCodesRepository confirmationCodesRepository;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    //private final PoolProjectMailSender mailSender;

    /* private final MailTemplatesUtil mailTemplatesUtil;

     @Value("${base.url}")
     private String baseUrl;
 */
    //@Transactional
    public UserDto register(NewUserDto newUser) {

        checkIfExistsByEmail(newUser);

        User user = saveNewUser(newUser);

        //String codeValue = UUID.randomUUID().toString();

        //saveConfirmCode(user, codeValue);

        //String link = createLinkForConfirmation(codeValue);

        //String html = mailTemplatesUtil.createConfirmationMail(user.getFirstName(), user.getLastName(), link);

        //mailSender.send(user.getEmail(), "Registration", html); // @Async

        createCart(user);

        return from(user);
    }

    private static void createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(cart);
    }
//
   /* private String createLinkForConfirmation(String codeValue) {
        return baseUrl +
               "/api/users/confirm/"
              //  "/api/users/confirm"
                + codeValue;
    }*/

    /*private void saveConfirmCode(User user, String codeValue) {
        ConfirmationCode code = ConfirmationCode.builder()
                .code(codeValue)
                .user(user)
                .expiredDateTime(LocalDateTime.now().plusMinutes(1))
                .build();
        if (usersRepository.existsByEmail(newUser.getEmail())) {
            throw new RestException(HttpStatus.CONFLICT,
                    "Пользователь с email <" + newUser.getEmail() + "> уже существует");
        }

        confirmationCodesRepository.save(code);
    }*/

    private User saveNewUser(NewUserDto newUser) {
        User user = User.builder()
                .email(newUser.getEmail())
                .hashPassword(passwordEncoder.encode(newUser.getPassword()))
                .role(User.Role.USER)
                .firstName(newUser.getFirstName())
                .lastName(newUser.getLastName())
                .state(User.State.NOT_CONFIRMED)
                .phoneNumber(newUser.getPhoneNumber())
                .build();

        usersRepository.save(user);

        return user;

    }

    private void checkIfExistsByEmail(NewUserDto newUser) {
        if (usersRepository.existsByEmail(newUser.getEmail())) {
            throw new RestException(HttpStatus.CONFLICT,
                    "User with email <" + newUser.getEmail() + "> already exists");
        }
    }

    public UserDto getUserById(Long currentUserId) {
        return UserDto.from(usersRepository.findById(currentUserId).orElseThrow(() ->
                new EntityNotFoundException("Пользователь с ID " + currentUserId + " не найден")));
    }

    public User findById(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    /*@Transactional
    public UserDto confirm(String confirmCode) {
        ConfirmationCode code = confirmationCodesRepository
                .findByCodeAndExpiredDateTimeAfter(confirmCode, LocalDateTime.now())
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Code not found or is expired"));

        User user = usersRepository
                .findFirstByCodesContains(code)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "User by code not found"));
    public void deleteUser(User user) {
        usersRepository.delete(user);
    }

        user.setState(User.State.CONFIRMED);

        usersRepository.save(user);

        return UserDto.from(user);
    }*/
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

    public UserDto deleteUser(User id) {
        // Находим пользователя по его идентификатору
        User userToDelete = usersRepository.findById(id.getId())
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND,
                        "User with id<" + id + "> not found"));
        UserDto deletedUserDto = UserDto.from(userToDelete);
        usersRepository.delete(userToDelete);

        // Возвращаем информацию о удаленном пользователе
        return deletedUserDto;
    }


}

