package users;

import jdbc.JdbcDAO;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UsersServiceImpl implements UsersService {
    private Scanner sc = new Scanner(System.in);


    @Override
    public String join() {
        while (true) {
            try {
                System.out.print("아이디를 입력하시오 : ");
                String userId = sc.next();

                System.out.print("비밀번호를 입력하시오 : ");
                String userPassword = sc.next();


                System.out.print("나이를 입력하시오 : ");
                int userAge = sc.nextInt();

                boolean result = JdbcDAO.getInstance().join(new UsersDTO(userId, userPassword, userAge));

                if (result) {
                    return userId;
                }
            } catch (InputMismatchException e) {
                System.out.println("잘못된 형식의 나이 입력입니다.");
                sc.nextLine();
            }
        }

    }

    @Override
    public String login() {
        String exit = "";
        System.out.print("아이디를 입력하시오 : ");
        String userId = sc.next();

        System.out.print("비밀번호를 입력하시오 : ");
        String userPassword = sc.next();

        boolean result = JdbcDAO.getInstance().login(userId, userPassword);
        if (result) {
            exit = userId;
        }
        return exit;
    }
}
