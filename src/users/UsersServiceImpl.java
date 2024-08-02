package users;

import jdbc.JdbcDAO;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UsersServiceImpl implements UsersService {
    private Scanner scanner = new Scanner(System.in);


    @Override
    public void join() {
        System.out.print("아이디를 입력하시오 : ");
        String userID = scanner.next();

        System.out.print("비밀번호를 입력하시오 : ");
        String userPassword = scanner.next();


        while (true) {
            try {
                System.out.print("나이를 입력하시오 : ");
                int userAge = scanner.nextInt();

                JdbcDAO.getInstance().join(new UsersDTO(userID, userPassword, userAge));
                break;
            } catch (InputMismatchException e) {
                System.out.println("잘못된 형식의 나이 입력입니다.");
                scanner.nextLine();
            }
        }
    }

    @Override
    public void login() {
        System.out.print("아이디를 입력하시오 : ");
        String userId = scanner.next();

        System.out.print("비밀번호를 입력하시오 : ");
        String userPassword = scanner.next();

        JdbcDAO.getInstance().login(userId, userPassword);
    }
}
