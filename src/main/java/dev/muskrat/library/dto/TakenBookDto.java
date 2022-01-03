package dev.muskrat.library.dto;

import dev.muskrat.library.dao.Book;
import dev.muskrat.library.dao.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;
import javax.persistence.Column;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TakenBookDto {

    private Book book;

    private User user;

    private Instant revertTime;

    private Instant bookUpTime;
}
