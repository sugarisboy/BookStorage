/*
package dev.muskrat.library.menu.commands;

import dev.muskrat.library.dao.Book;
import dev.muskrat.library.dao.Genre;
import dev.muskrat.library.dao.User;
import dev.muskrat.library.exception.BadRequestException;
import dev.muskrat.library.repository.BookRepository;
import dev.muskrat.library.repository.UserRepository;
import dev.muskrat.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BooksCommand extends CommandMenu {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    public BooksCommand() {
        super("books");
    }

    @Override
    public void run(String[] args)  {
        List<Book> books = bookRepository.findAll();

        if (args.length == 1) {
            print(books);
        } else if (args.length == 4) {

            User user = convertArgIdToUser(args[1]);
            books = bookService.safeSort(user, books);

            if (args[2].equalsIgnoreCase("sort")) {
                if (args[3].equalsIgnoreCase("writer")) {
                    print(bookService.findSortByWriter(books));
                } else if (args[3].equalsIgnoreCase("title")) {
                    print(bookService.findSortByTitle(books));
                } else {
                    print(books);
                }
            }

        } else if (args.length == 5 && args[2].equalsIgnoreCase("genre")) {

            User user = convertArgIdToUser(args[3]);

            Genre genre = null;
            try {
                genre = Genre.valueOf(args[4].toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Genre is not valid data");
            }

            List<Book> byGenre = bookService.findByGenre(genre);
            byGenre = bookService.safeSort(user, byGenre);
            print(byGenre);

        } else if (args.length == 5 && args[2].equalsIgnoreCase("writer")) {

            User user = convertArgIdToUser(args[3]);
            String writer = args[4].replace("_", " ");
            List<Book> byWriter = bookService.findByWriter(writer);
            byWriter = bookService.safeSort(user, byWriter);
            print(byWriter);

        }
    }

    private User convertArgIdToUser(String arg) {
        if (!arg.matches("[0-9]+"))
            throw new BadRequestException("User id is not valid data");
        Long userId = Long.valueOf(arg);
        return userRepository.findById(userId).orElseThrow(
            () -> new BadRequestException("User with id " + arg + " not found")
        );
    }

    private void print(List<Book> books) {
        System.out.printf("%3s %20s %30s %15s %5s %5s\n",
            "id",
            "writer",
            "title",
            "genre",
            "age",
            "count"
        );
        books.forEach(System.out::println);
    }
}
*/
