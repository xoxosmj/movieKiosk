package user;

import java.util.Scanner;

public class UsersServiceImpl implements UsersService {
    private Scanner scanner = new Scanner(System.in);


    @Override
    public void join() {
        System.out.print("아이디를 입력하시오 : ");
        String userId = scanner.next();

        System.out.print("비밀번호를 입력하시오 : ");
        String userPassword = scanner.next();

        System.out.print("나이를 입력하시오 : ");
        int age = scanner.nextInt();

        UsersDAO.getInstance().join(new UsersDTO(userId,userPassword,age));
    }

    @Override
    public void login() {
        System.out.print("아이디를 입력하시오 : ");
        String userId = scanner.next();

        System.out.print("비밀번호를 입력하시오 : ");
        String userPassword = scanner.next();

        UsersDAO.getInstance().login(userId,userPassword);
    }
}
