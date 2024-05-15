package de.ait.pool.dto.userDto;

import de.ait.pool.models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "User", description = "Данные пользователя")
public class UserDto {

    @Schema(description = "идентификатор пользователя", example = "1")
    private Long id;

    @Schema(description = "имя пользователя", example = "Anton")
    private String firstName;

    @Schema(description = "фамилия пользователя", example = "Ivanov")
    private String lastName;

    @Schema(description = "Email пользователя", example = "user@mail.com")
    private String email;

    @Schema(description = "Роль пользователя", example = "USER")
    private String role;

    @Schema(description = "Номер телефона пользователя", example = "+7 952 889 01 88")
    private String phoneNumber;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public static List<UserDto> from(Collection<User> users) {
        return users.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

}
