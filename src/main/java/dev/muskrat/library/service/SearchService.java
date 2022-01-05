package dev.muskrat.library.service;

import dev.muskrat.library.dao.Book;
import dev.muskrat.library.dao.User;
import dev.muskrat.library.dto.BookFilterDto;
import dev.muskrat.library.dto.BookSort;
import dev.muskrat.library.dto.TakenBookDto;
import dev.muskrat.library.exception.UserNotFoundException;
import dev.muskrat.library.repository.TakenBookRepository;
import dev.muskrat.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final BookService bookService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final TakenBookRepository takenBookRepository;

    public List<Book> searchBooks(BookFilterDto bookFilterDto) {
        List<Book> books = bookService.findByGenreAndWriter(
                bookFilterDto.getGenre(),
                bookFilterDto.getWriter()
        );

        if (bookFilterDto.getUserId() != null) {
            User user = userRepository
                    .findById(bookFilterDto.getUserId())
                    .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
            books = bookService.safeSort(user, books);
        }

        Map<BookSort, Function<List<Book>, List<Book>>> mapSort = Map.of(
                BookSort.NONE, list -> list,
                BookSort.BY_TITLE, bookService::findSortByTitle,
                BookSort.BY_WRITER, bookService::findSortByWriter
        );

        Function<List<Book>, List<Book>> sortFunction = mapSort.get(bookFilterDto.getSortBy());

        return sortFunction.apply(books);
    }

    public List<String> searchWriters() {
        return bookService.searchWriters();
    }

    public List<TakenBookDto> findTakenBooks() {
        return takenBookRepository.findAll().stream()
                .map(takenBook ->
                        TakenBookDto.builder()
                                .book(takenBook.getBook())
                                .user(takenBook.getUser())
                                .bookUpTime(takenBook.getCreated())
                                .revertTime(takenBook.getExpired())
                                .build()
                )
                .collect(Collectors.toList());
    }
}
