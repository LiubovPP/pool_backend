package de.ait.pool.service;


import de.ait.pool.dto.userDto.NewUserDto;
import de.ait.pool.dto.userDto.UserDto;
import de.ait.pool.exceptions.RestException;
//import de.ait.pool.mail.MailTemplatesUtil;
//import de.ait.pool.mail.PoolProjectMailSender;
//import de.ait.pool.models.ConfirmationCode;
import de.ait.pool.models.User;
//import de.ait.pool.repository.ConfirmationCodesRepository;
import de.ait.pool.repository.UserRepository;
//import freemarker.template.Configuration;
import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;
import static de.ait.pool.dto.userDto.UserDto.from;


@RequiredArgsConstructor
@Service
public class UsersService {

    private final UserRepository usersRepository;

   // private final ConfirmationCodesRepository confirmationCodesRepository;

    private final PasswordEncoder passwordEncoder;

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

        return from(user);
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
        return from(usersRepository.findById(currentUserId).orElseThrow());
    }

    /*@Transactional
    public UserDto confirm(String confirmCode) {
        ConfirmationCode code = confirmationCodesRepository
                .findByCodeAndExpiredDateTimeAfter(confirmCode, LocalDateTime.now())
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Code not found or is expired"));

        User user = usersRepository
                .findFirstByCodesContains(code)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "User by code not found"));

        user.setState(User.State.CONFIRMED);

        usersRepository.save(user);

        return UserDto.from(user);
    }*/
}
