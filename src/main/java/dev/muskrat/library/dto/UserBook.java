package dev.muskrat.library.dto;

import dev.muskrat.library.dao.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBook {

    private Long id;
    private String writer;
    private String title;
    private Genre genre;
    private Integer ageLimit;
    private Integer count;
    private Instant revertTime;
    private Instant bookUpTime;
}
