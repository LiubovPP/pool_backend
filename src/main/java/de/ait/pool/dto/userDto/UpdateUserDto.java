package de.ait.pool.dto.userDto;


import de.ait.pool.models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;



@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
@Schema(name = "UpdateUser", description = "Данные пользователя")
public class UpdateUserDto {
    @Schema(description = "Имя пользователя", example = "Alex")
    private String firstName;
    @Schema(description = "Фамилия пользователя", example = "Müller")
    private String lastName;
    @Schema(description = "Роль пользователя", example = "USER")
    private String role;
    @Schema(description = "Номер телефона пользователя", example = "+7 952 889 01 88")
    private String phoneNumber;


}

