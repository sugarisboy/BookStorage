package dev.muskrat.library.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "writer")
    private String writer;

    @Column(name = "title")
    private String title;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "genre")
    private Genre genre;

    @Column(name = "age")
    private Integer ageLimit;

    @Column(name = "count")
    private Integer count;

    @OneToMany
    @JoinTable(name="book_takenbook",
        joinColumns = @JoinColumn(name="book_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name="taken_id", referencedColumnName="id")
    )
    private List<TakenBook> users;
}
