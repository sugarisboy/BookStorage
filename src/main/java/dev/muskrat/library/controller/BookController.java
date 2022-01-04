package dev.muskrat.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.muskrat.library.dao.Book;
import dev.muskrat.library.dto.CreateBookDto;
import dev.muskrat.library.service.BookService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import javax.validation.Valid;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final ObjectMapper objectMapper;
    private final BookService bookService;

    @PostMapping("/create")
    @ApiOperation("Добавление книги в базу")
    public void createBook(@RequestBody @Valid CreateBookDto bookDto) {
        Book book = objectMapper.convertValue(bookDto, Book.class);
        bookService.addBook(book);
    }

    @GetMapping("/list")
    @ApiOperation("Получение всех книг")
    public List<Book> getListBooks() {
        return bookService.findSortByTitle(bookService.findAll());
    }

    @GetMapping("/{id}")
    @ApiOperation("Получение книги по ID")
    public Book getBook(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Удаление книги")
    public void delete(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
