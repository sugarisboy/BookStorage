/*
package dev.muskrat.library.menu.commands;

import dev.muskrat.library.dao.Book;
import dev.muskrat.library.dao.User;
import dev.muskrat.library.dto.ReturnBookDTO;
import dev.muskrat.library.exception.BadRequestException;
import dev.muskrat.library.exception.BookNotFoundException;
import dev.muskrat.library.repository.BookRepository;
import dev.muskrat.library.repository.UserRepository;
import dev.muskrat.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class UserCommand extends CommandMenu {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserService userService;

    public UserCommand() {
        super("user");
    }

    @Override
    public void run(String[] args) {
        if (args.length == 8 && args[1].equalsIgnoreCase("add")) {
            String firstName = args[2];
            String lastName = args[3];
            String thirdName = args[4];

            if (!args[5].matches("[0-9]+"))
                throw new BadRequestException("Day is not valid data");
            Integer day = Integer.valueOf(args[5]);

            if (!args[6].matches("[0-9]+"))
                throw new BadRequestException("Month is not valid data");
            Integer month = Integer.valueOf(args[6]);

            if (!args[7].matches("[0-9]+"))
                throw new BadRequestException("Year is not valid data");
            Integer year = Integer.valueOf(args[7]);

            ZonedDateTime date = ZonedDateTime.of(year, month, day, 0, 0, 0, 0, ZoneId.of("UTC"));
            Instant birthday;
            try {
                birthday = Instant.from(date);
            } catch (Exception e) {
                e.printStackTrace();
                throw new BadRequestException("Date with incorrect");
            }

            User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .thirdName(thirdName)
                .birthday(birthday)
                .build();

            user = userService.register(user);

            System.out.println("Новый пользователь:\n" + user);
        } else if (args.length == 3 && args[1].equalsIgnoreCase("delete")) {
            User user = convertArgIdToUser(args[2]);
            userService.delete(user);
            System.out.println("Пользователь удален.");
        } else if (args.length == 4) {
            if (args[1].equalsIgnoreCase("addbook")) {
                User user = convertArgIdToUser(args[2]);
                Book book = convertArgIdToBook(args[3]);
                try {
                    userService.lendBook(user, book);
                    System.out.println("Книга выдана.");
                } catch (BookNotFoundException e) {
                    System.out.println("Книги нет в наличии.");
                }
            } else if (args[1].equalsIgnoreCase("takebook")) {
                String user_id = args[2];
                if (!user_id.matches("[0-9]+"))
                    throw new BadRequestException("User id is not valid data");
                Long userId = Long.valueOf(user_id);

                String book_id = args[3];
                if (!book_id.matches("[0-9]+"))
                    throw new BadRequestException("Book id is not valid data");
                Long bookId = Long.valueOf(book_id);

                ReturnBookDTO returnBookDTO = userService.returnBook(bookId, userId);

                System.out.println("Книга возвращена\nШтраф: " + returnBookDTO.getFine());
            }
        }
    }

    private Book convertArgIdToBook(String book_id) {
        if (!book_id.matches("[0-9]+"))
            throw new BadRequestException("Book id is not valid data");
        Long bookId = Long.valueOf(book_id);
        return bookRepository.findById(bookId).orElseThrow(
            () -> new BadRequestException("Book with id " + book_id + " not found")
        );
    }

    private User convertArgIdToUser(String arg) {
        if (!arg.matches("[0-9]+"))
            throw new BadRequestException("User id is not valid data");
        Long userId = Long.valueOf(arg);
        return userRepository.findById(userId).orElseThrow(
            () -> new BadRequestException("User with id " + arg + " not found")
        );
    }
}
*/
