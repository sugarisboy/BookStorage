//package dev.muskrat.library.menu.commands;
//
//import dev.muskrat.library.dao.Book;
//import dev.muskrat.library.dao.Genre;
//import dev.muskrat.library.exception.BadRequestException;
//import dev.muskrat.library.repository.BookRepository;
//import dev.muskrat.library.repository.UserRepository;
//import dev.muskrat.library.service.BookService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class BookCommand extends CommandMenu {
//
//    @Autowired
//    private BookRepository bookRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private BookService bookService;
//
//    public BookCommand() {
//        super("book");
//    }
//
//    @Override
//    public void run(String[] args) {
//        if (args.length < 2) {
//            System.out.println("book add <title> <writer> <genre> <age limit> <count>");
//            System.out.println("book delete <book id>");
//        } else if (args.length == 3 && args[1].equalsIgnoreCase("delete") ) {
//            Book book = convertArgIdToBook(args[2]);
//            bookService.removeBook(book);
//            System.out.println("Книга была удалена.");
//        } else if (args.length == 7 && args[1].equalsIgnoreCase("add")) {
//            Book book = add(args);
//            System.out.println("Добавлена книга:\n" + book);
//        }
//    }
//
//    private Book add(String[] args) {
//        String title = args[2].replace("_", " ");
//        String writer = args[3].replace("_", " ");
//
//        Genre genre = null;
//        try {
//            genre = Genre.valueOf(args[4].toUpperCase());
//        } catch (IllegalArgumentException e) {
//            throw new BadRequestException("Genre is not valid data");
//        }
//
//        if (!args[5].matches("[0-9]+"))
//            throw new BadRequestException("Age limit is not valid data");
//        Integer ageLimit = Integer.valueOf(args[5]);
//
//        if (!args[6].matches("[0-9]+"))
//            throw new BadRequestException("Count is not valid data");
//        Integer count = Integer.valueOf(args[6]);
//
//        Book book = Book.builder()
//            .title(title)
//            .writer(writer)
//            .genre(genre)
//            .ageLimit(ageLimit)
//            .count(count)
//            .build();
//
//        bookService.addBook(book);
//        return book;
//    }
//
//    private Book convertArgIdToBook(String book_id) {
//        if (!book_id.matches("[0-9]+"))
//            throw new BadRequestException("Book id is not valid data");
//        Long bookId = Long.valueOf(book_id);
//        return bookRepository.findById(bookId).orElseThrow(
//            () -> new BadRequestException("Book with id " + book_id + " not found")
//        );
//    }
//}
