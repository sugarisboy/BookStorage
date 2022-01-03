package dev.muskrat.library.controller;

import dev.muskrat.library.dao.Book;
import dev.muskrat.library.dao.Genre;
import dev.muskrat.library.dto.BookFilterDto;
import dev.muskrat.library.dto.TakenBookDto;
import dev.muskrat.library.service.SearchService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collection;
import java.util.List;
import javax.validation.Valid;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/books")
    @ApiOperation("Получить список книг")
    public List<Book> searchBooks(@Valid BookFilterDto bookFilterDto) {
        return searchService.searchBooks(bookFilterDto);
    }

    @GetMapping("/genres")
    @ApiOperation("Получить список жанров")
    public Genre[] genres() {
        return Genre.values();
    }

    @GetMapping("/writers")
    @ApiOperation("Получить список писателей")
    public Collection<String> writers() {
        return searchService.searchWriters();
    }

    @GetMapping("/taken-books")
    @ApiOperation("Получение списка выданных книг")
    public Collection<TakenBookDto> takenBooks() {
        return searchService.findTakenBooks();
    }
}
