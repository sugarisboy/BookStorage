package dev.muskrat.library.service;

import dev.muskrat.library.dao.Book;
import dev.muskrat.library.dao.User;
import dev.muskrat.library.dto.BookFilterDto;
import dev.muskrat.library.dto.BookSort;
import dev.muskrat.library.dto.TakenBookDto;
import dev.muskrat.library.repository.TakenBookRepository;
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
    private final TakenBookRepository takenBookRepository;

    public List<Book> searchBooks(BookFilterDto bookFilterDto) {
        User user = userService.findById(bookFilterDto.getUserId());

        List<Book> books = bookService.findByGenreAndWriter(
                bookFilterDto.getGenre(),
                bookFilterDto.getWriter()
        );

        List<Book> safeBooks = bookService.safeSort(user, books);

        Map<BookSort, Function<List<Book>, List<Book>>> mapSort = Map.of(
                BookSort.NONE, list -> list,
                BookSort.BY_TITLE, bookService::findSortByTitle,
                BookSort.BY_WRITER, bookService::findSortByWriter
        );

        Function<List<Book>, List<Book>> sortFunction = mapSort.get(bookFilterDto.getSortBy());

        return sortFunction.apply(safeBooks);
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
