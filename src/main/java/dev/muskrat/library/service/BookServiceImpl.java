package dev.muskrat.library.service;

import dev.muskrat.library.dao.Book;
import dev.muskrat.library.dao.Genre;
import dev.muskrat.library.dao.TakenBook;
import dev.muskrat.library.dao.User;
import dev.muskrat.library.exception.BookNotFoundException;
import dev.muskrat.library.exception.DeleteBookFailedException;
import dev.muskrat.library.exception.DeleteUserFailedException;
import dev.muskrat.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final UserService userService;

    @Override
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = findById(id);

        List<TakenBook> takenBooks = book.getUsers();
        if (!takenBooks.isEmpty()) {
            String bookTitles = takenBooks.stream()
                    .map(TakenBook::getUser)
                    .map(user -> String.format("%s %s", user.getLastName(), user.getFirstName()))
                    .reduce((a, b) -> a + ", " + b)
                    .orElseThrow();

            throw new DeleteBookFailedException("Невозможно удалить книгу, так как она не сдана для: " + bookTitles);
        }

        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> findByGenre(Genre genre) {
        return bookRepository.findAll().stream()
            .filter(book -> book.getGenre().equals(genre))
            .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByWriter(String writer) {
        return bookRepository.findAll().stream()
            .filter(book -> book.getWriter().toLowerCase().contains(writer.toLowerCase()))
            .collect(Collectors.toList());
    }

    @Override
    public List<Book> safeSort(User user, List<Book> books) {
        long age = userService.userAge(user);

        return books.stream()
            .filter(book -> book.getAgeLimit() <= age)
            .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> findSortByWriter(List<Book> books) {
        return books.stream()
            .sorted(Comparator.comparing(Book::getWriter))
            .collect(Collectors.toList());
    }

    @Override
    public List<Book> findSortByTitle(List<Book> books) {
        return books.stream()
            .sorted(Comparator.comparing(Book::getTitle))
            .collect(Collectors.toList());
    }

    @Override
    public Book findById(Long id) {
        return bookRepository
                .findById(id)
                .orElseThrow(() -> new BookNotFoundException("Книга не найдена"));
    }

    @Override
    public List<Book> findByGenreAndWriter(Genre genre, String writer) {
        return bookRepository.findAll().stream()
                .filter(book -> genre == null || book.getGenre().equals(genre))
                .filter(book -> writer == null || book.getWriter().toLowerCase().contains(writer.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> searchWriters() {
        return bookRepository.findAll().stream()
                .map(Book::getWriter)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
