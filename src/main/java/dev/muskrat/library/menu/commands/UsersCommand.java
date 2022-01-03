//package dev.muskrat.library.menu.commands;
//
//import dev.muskrat.library.repository.TakenBookRepository;
//import dev.muskrat.library.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//
//public class UsersCommand extends CommandMenu {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private TakenBookRepository takenBookRepository;
//
//    public UsersCommand() {
//        super("users");
//    }
//
//    @Override
//    public void run(String[] args) {
//        System.out.printf("%3s %12s %12s %16s %15s\n",
//            "id",
//            "name",
//            "lastName",
//            "thirdName",
//            "birthday"
//        );
//        userRepository.findAll().forEach(System.out::println);
//        System.out.println("___________________________________________________\n");
//        System.out.printf("%4s%4s%30s %15s %15s\n",
//            "usr",
//            "book",
//            "title",
//            "taken",
//            "expired"
//        );
//        takenBookRepository.findAll().forEach(System.out::println);
//    }
//}
