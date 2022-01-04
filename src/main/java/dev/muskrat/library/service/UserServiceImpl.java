package dev.muskrat.library.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.muskrat.library.dao.Book;
import dev.muskrat.library.dao.TakenBook;
import dev.muskrat.library.dao.User;
import dev.muskrat.library.dto.CreateUserDto;
import dev.muskrat.library.dto.ReturnBookDTO;
import dev.muskrat.library.dto.UserBook;
import dev.muskrat.library.dto.UserDto;
import dev.muskrat.library.exception.BadRequestException;
import dev.muskrat.library.exception.BookNotFoundException;
import dev.muskrat.library.exception.DeleteUserFailedException;
import dev.muskrat.library.repository.BookRepository;
import dev.muskrat.library.repository.TakenBookRepository;
import dev.muskrat.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final TakenBookRepository takenBookRepository;
    private final ObjectMapper objectMapper;

    @Value("${app.expire.book.days}")
    private int bookExpireDays;

    @Value("${app.expire.book.fine}")
    private double bookExpireFine;

    @Override
    public User register(CreateUserDto dto) {
        User user = objectMapper.convertValue(dto, User.class);

        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void lendBook(Long userId, Long bookId) throws BookNotFoundException {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new BookNotFoundException("Пользователь не найден"));

        Book book = bookRepository.getOne(bookId);

        if (book.getCount() < 1)
            throw new BookNotFoundException(
                    String.format("Книги \"%s\" нет в наличии", book.getTitle())
            );

        book.setCount(book.getCount() - 1);
        bookRepository.save(book);

        Instant now = Instant.now();

        TakenBook takenBook = new TakenBook();
        takenBook.setUser(user);
        takenBook.setBook(book);
        takenBook.setCreated(now.truncatedTo(ChronoUnit.DAYS));
        takenBook.setExpired(now.plus(bookExpireDays, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS));

        takenBookRepository.save(takenBook);
    }

    @Override
    public ReturnBookDTO returnBook(Long userId, Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new BadRequestException("Книга " + bookId + " не найдена")
        );

        List<TakenBook> usersTakeBook = book.getUsers();
        TakenBook takenBook = usersTakeBook.stream()
                .filter(b -> b.getUser().getId().equals(userId))
                .min((a, b) -> a.getExpired().getNano() < b.getExpired().getNano() ? 1 : -1)
                .orElseThrow(
                        () -> new BadRequestException(userId + " не брал книгу эту книгу!")
                );

        Instant now = Instant.now().truncatedTo(ChronoUnit.DAYS);
        Instant expired = takenBook.getExpired().truncatedTo(ChronoUnit.DAYS);
        long daysFine = betweenInstant(ChronoUnit.DAYS, expired, now);
        double fine = daysFine > 0 ? daysFine * bookExpireFine : 0;

        takenBookRepository.delete(takenBook);
        book.setCount(book.getCount() + 1);

        return ReturnBookDTO.builder()
                .fine(fine)
                .build();
    }

    @Override
    public UserDto findById(Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new BookNotFoundException("Пользователь не найден"));

        return convertUserToDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new BookNotFoundException("Пользователь не найден"));


        List<TakenBook> takenBooks = user.getBooks();
        if (!takenBooks.isEmpty()) {
            String bookTitles = takenBooks.stream()
                    .map(TakenBook::getBook)
                    .map(Book::getTitle)
                    .reduce((a, b) -> a + ", " + b)
                    .orElseThrow();

            throw new DeleteUserFailedException("Невозможно удалить пользователя, так как у него не сданы: " + bookTitles);
        }

        userRepository.deleteById(id);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .sorted(Comparator.comparing(User::getLastName))
                .map(this::convertUserToDto)
                .collect(Collectors.toList());
    }

    @Override
    public long userAge(User user) {
        Instant now = Instant.now();
        Instant expired = user.getBirthday()
                .atTime(0, 0)
                .toInstant(ZoneOffset.UTC);

        return ChronoUnit.YEARS.between(
                OffsetDateTime.ofInstant(expired, ZoneOffset.UTC),
                OffsetDateTime.ofInstant(now, ZoneOffset.UTC));
    }

    private long betweenInstant(ChronoUnit chronoUnit, Instant start, Instant end) {
        return chronoUnit.between(
                OffsetDateTime.ofInstant(start, ZoneOffset.UTC),
                OffsetDateTime.ofInstant(end, ZoneOffset.UTC));
    }

    private UserDto convertUserToDto(User user) {
        UserDto userDto = objectMapper.convertValue(user, UserDto.class);

        List<UserBook> books = user.getBooks().stream().map(book -> UserBook.builder()
                .ageLimit(book.getBook().getAgeLimit())
                .count(book.getBook().getCount())
                .genre(book.getBook().getGenre())
                .id(book.getBook().getId())
                .title(book.getBook().getTitle())
                .writer(book.getBook().getWriter())
                .revertTime(book.getExpired())
                .bookUpTime(book.getCreated())
                .build()
        ).collect(Collectors.toList());

        userDto.setBooks(books);

        return userDto;
    }
}
