package users;

import jdbc.JdbcDAO;

import java.util.Scanner;

public class UsersServiceImpl implements UsersService {
    private Scanner sc = new Scanner(System.in);

    @Override
    public String login() {
        System.out.print("아이디를 입력하세요 : ");
        String userId = sc.next();

        System.out.print("비밀번호를 입력하세요 : ");
        String userPassword = sc.next();

        boolean result = JdbcDAO.getInstance().login(userId, userPassword);
        if (result) {
            return userId;
        } else {
            return "";
        }
    }

    @Override
    public String join() {
        System.out.print("아이디를 입력하시오 : ");
        String userId = sc.next();

        System.out.print("비밀번호를 입력하시오 : ");
        String userPassword = sc.next();

        System.out.print("나이를 입력하시오 : ");
        int userAge = sc.nextInt();

        boolean result = JdbcDAO.getInstance().join(new UsersDTO(userId, userPassword, userAge));

        if (result) {
            return userId;
        } else {
            return "";
        }
    }
}
