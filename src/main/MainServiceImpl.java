package main;


import lombok.RequiredArgsConstructor;
import reservation.ReservationMain;
import users.UsersService;

import java.util.Scanner;


@RequiredArgsConstructor
public class MainServiceImpl implements MainService {
    private final UsersService userService;
    private Scanner sc = new Scanner(System.in);

    @Override
    public void execute() {
        while (true) {

            String returnId = loginPage();

            if (returnId.equals("")) {
                System.out.println("프로그램 종료");
                return;
            }
            ReservationMain reservationMain = new ReservationMain();
            reservationMain.Reservation(returnId);
        }
    }

    public String loginPage() {
        String returnId;

        while (true) {
            System.out.println("*************************************");
            System.out.println("*                                   *");
            System.out.println("*        Welcome to Movie Kiosk     *");
            System.out.println("*                                   *");
            System.out.println("*************************************");
            System.out.println();
            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");
            System.out.println("3. 프로그램 종료.");
            System.out.print("메뉴를 선택해주세요 : ");
            int choice = sc.nextInt();

            switch (choice) {
                case 3:
                    return "";

                case 1:
                    returnId = userService.login();
                    if (!returnId.equals("")) {
                        return returnId;
                    }
                    break;
                case 2:
                    returnId = userService.join();
                    if (!returnId.equals("")) {
                        return returnId;
                    }
                    break;
                default:
                    System.out.println("올바른 번호를 입력하세요");
                    break;
            }
        }

    }
}