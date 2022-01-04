package dev.muskrat.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {

    @NotBlank(message = "Имя не должно быть пустым")
    private String firstName;

    @NotBlank(message = "Фамилия не должна быть пустым")
    private String lastName;

    @NotBlank(message = "Отчеством не должно быть пустым")
    private String thirdName;

    @NotNull(message = "Не указана дата рождения")
    private Instant birthday;
}
