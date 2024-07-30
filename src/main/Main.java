package main;

import user.UserService;
import user.UserServiceImpl;

public class Main {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        MainServiceImpl mainService = new MainServiceImpl(userService);
        mainService.execute();
    }
}
