package dev.muskrat.library.service;

import dev.muskrat.library.dao.Book;
import dev.muskrat.library.dao.User;
import dev.muskrat.library.dto.ReturnBookDTO;

public interface UserService {

    User register(User user);

    void delete(User user);

    void lendBook(Long user, Long book);

    long userAge(User user);

    ReturnBookDTO returnBook(Long userId, Long bookId);

    User findById(Long userId);
}
