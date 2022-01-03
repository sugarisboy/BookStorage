package dev.muskrat.library.service;

import dev.muskrat.library.dao.Book;
import dev.muskrat.library.dao.Genre;
import dev.muskrat.library.dao.User;

import java.util.List;

public interface BookService {

    void addBook(Book book);

    void deleteBook(Long id);

    List<Book> findByGenre(Genre genre);

    List<Book> findByWriter(String writer);

    List<Book> safeSort(User user, List<Book> books);

    List<Book> findAll();

    List<Book> findSortByWriter(List<Book> books);

    List<Book> findSortByTitle(List<Book> books);

    Book findById(Long id);

    List<Book> findByGenreAndWriter(Genre genre, String writer);

    List<String> searchWriters();
}
