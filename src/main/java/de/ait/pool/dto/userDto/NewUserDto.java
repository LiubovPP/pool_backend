package de.ait.pool.dto.userDto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
@Schema(name = "NewUser", description = "Данные для регистрации")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {

    @NotNull
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ\\\\-]+$", message = "Имя должно содержать только буквы и тире")
    @Schema(description = "Имя пользователя", example = "Kirill")
    private String firstName;

    @NotNull
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ\\\\-]+$", message = "Имя должно содержать только буквы и тире")
    @Schema(description = "Фамилия пользователя", example = "Topolcean")
    private String lastName;

    @NonNull
    @Schema(description = "Номер телефона", example = "+7 952 889 01 88")
    //@Pattern(regexp = "^\\\\+7 \\\\d{3} \\\\d{3} \\\\d{2} \\\\d{2}$", message = "Номер телефона в формате +7 952 889 01 88")
    private String phoneNumber;

    @Email
    @NotNull
    @Schema(description = "Email пользователя", example = "user@mail.com")
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")
    @Schema(description = "Пароль пользователя", example = "Qwerty007!")
    private String password;

    @NotNull
    @Pattern(regexp = "[^a-zA-Z]{1,30}")
    @Schema(description = "Номер телефона пользователя", example = "+7 952 889 01 88")
    private String phoneNumber;
}
