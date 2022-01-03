package dev.muskrat.library.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "taken_books")
@AllArgsConstructor
@NoArgsConstructor
public class TakenBook {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinTable(name="book_takenbook",
        joinColumns = @JoinColumn(name="taken_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name="book_id", referencedColumnName="id")
    )
    private Book book;

    @JsonIgnore
    @ManyToOne
    @JoinTable(name="user_takenbook",
        joinColumns = @JoinColumn(name="taken_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name="user_id", referencedColumnName="id")
    )
    private User user;

    @Column(name = "expired")
    private Instant expired;

    @Column(name = "created")
    private Instant created;

    public String toString() {
        return String.format("%3d %3d %30s %15s %15s",
            user.getId(),
            book.getId(),
            book.getTitle(),
            new SimpleDateFormat("dd MMM YYYY").format(Date.from(created)),
            new SimpleDateFormat("dd MMM YYYY").format(Date.from(expired))
        );
    }
}
