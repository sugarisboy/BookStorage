package dev.muskrat.library.controller;

import dev.muskrat.library.dao.Book;
import dev.muskrat.library.dao.User;
import dev.muskrat.library.dto.CreateUserDto;
import dev.muskrat.library.dto.ReturnBookDTO;
import dev.muskrat.library.dto.UserDto;
import dev.muskrat.library.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    @ApiOperation("Создание пользователя")
    public User createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        return userService.register(createUserDto);
    }

    @GetMapping("/{id}")
    @ApiOperation("Получить пользователя по ID")
    public UserDto getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping("/book-up")
    @ApiOperation("Взять книгу для пользователя")
    public void bookUp(
            @RequestParam @ApiParam("ID пользователя") Long userId,
            @RequestParam @ApiParam("ID книги") Long bookId
    ) {
        userService.lendBook(userId, bookId);
    }

    @PostMapping("/book-revert")
    @ApiOperation("Вернуть книгу для пользователя")
    public ReturnBookDTO bookRevert(
            @RequestParam @ApiParam("ID пользователя") Long userId,
            @RequestParam @ApiParam("ID книги") Long bookId
    ) {
        return userService.returnBook(userId, bookId);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Удаление пользователя")
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/list")
    @ApiOperation("Получение всех пользователей")
    public List<UserDto> getListBooks() {
        return userService.findAll();
    }
}
