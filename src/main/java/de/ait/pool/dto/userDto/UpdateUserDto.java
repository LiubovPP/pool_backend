package de.ait.pool.dto.userDto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;



@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
@Schema(name = "UpdateUser", description = "Данные пользователя")
public class UpdateUserDto {
    @Schema(name = "Имя пользователя", description = "Alex")
    private String firstName;
    @Schema(name = "Фамилия пользователя", description = "Müller")
    private String lastName;
    @Schema(description = "Номер телефона пользователя", example = "+7 952 889 01 88")
    private String phoneNumber;
    @Schema(description = "Роль пользователя", example = "USER")
    private String role;




}

