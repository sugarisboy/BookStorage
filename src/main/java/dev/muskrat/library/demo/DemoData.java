package dev.muskrat.library.demo;

import dev.muskrat.library.dao.Book;
import dev.muskrat.library.dao.Genre;
import dev.muskrat.library.dao.User;
import dev.muskrat.library.repository.BookRepository;
import dev.muskrat.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
public class DemoData {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @PostConstruct
    public void load() {
        User firstUser = User.builder()
            .firstName("Никита")
            .lastName("Смирнов")
            .thirdName("Александрович")
            .birthday(dateOf(2000, 8, 3))
            .build();

        User secondUser = User.builder()
            .firstName("Ирина")
            .lastName("Иванова")
            .thirdName("Алексеевна")
            .birthday(dateOf(1973, 12, 23))
            .build();

        User thirdUser = User.builder()
            .firstName("Артем")
            .lastName("Пашков")
            .thirdName("Сергеевич")
            .birthday(dateOf(2007, 9, 3))
            .build();

        Book book1 = Book.builder()
            .writer("Л. Н. Толстой")
            .title("Война и мир: Том I")
            .ageLimit(16)
            .count(3)
            .genre(Genre.CLASSIC)
            .build();

        Book book2 = Book.builder()
            .writer("Л. Н. Толстой")
            .title("Война и мир: Том II")
            .ageLimit(16)
            .count(0)
            .genre(Genre.CLASSIC)
            .build();

        Book book3 = Book.builder()
            .writer("Л. Н. Толстой")
            .title("Война и мир: Том III")
            .ageLimit(12)
            .count(1)
            .genre(Genre.CLASSIC)
            .build();

        Book book4 = Book.builder()
            .writer("А. Б. Вгдейкин")
            .title("Путешествие на марс")
            .ageLimit(10)
            .count(2)
            .genre(Genre.FANTASTIC)
            .build();

        Book book5 = Book.builder()
            .writer("Т. П. Независимая")
            .title("История черной кошечки")
            .ageLimit(40)
            .count(2)
            .genre(Genre.DRAMA)
            .build();

        userRepository.save(firstUser);
        userRepository.save(secondUser);
        userRepository.save(thirdUser);

        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        bookRepository.save(book4);
        bookRepository.save(book5);

        System.out.println();
    }


    private Instant dateOf(int year, int month, int day) {
        return LocalDate
            .of(year, month, day)
            .atTime(0, 0)
            .toInstant(ZoneOffset.UTC);
    }
}
