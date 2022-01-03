package dev.muskrat.library.dto;

import dev.muskrat.library.dao.Genre;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Фильтр книг")
public class BookFilterDto {

    @ApiModelProperty("Жанр")
    private Genre genre;

    @ApiModelProperty("Писатель")
    private String writer;

    @ApiModelProperty("Пользователь")
    @NotNull(message = "Пользователь не указан")
    @Positive(message = "ID пользователя должно быть больше нуля")
    private Long userId;

    @ApiModelProperty("Сортировать по полю")
    @NotNull(message = "Не указан параметр сортировки")
    private BookSort sortBy;
}
