package dev.muskrat.library.dto;

import dev.muskrat.library.dao.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookDto {

    @NotBlank(message = "Название книги не может быть пустым")
    private String title;

    @NotBlank(message = "Писатель не может быть пустым")
    private String writer;

    @NotNull(message = "Жанр должен быть указан")
    private Genre genre;

    @Max(value = 100, message = "Возраст не должен быть выше ста")
    @PositiveOrZero(message = "Возраст должен быть от 0 и больше")
    private int ageLimit;

    @PositiveOrZero(message = "Количество книг может быть от 0 и больше")
    private int count;
}
