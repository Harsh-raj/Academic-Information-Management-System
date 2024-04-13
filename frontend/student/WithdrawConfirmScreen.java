package frontend.student;

import backend.StudentService;
import frontend.ProtectedScreen;

import java.sql.SQLException;
import java.util.Scanner;

public class WithdrawConfirmScreen extends ProtectedScreen {
    private Integer registrationId;

    public WithdrawConfirmScreen(String title, String option, Integer registrationId) {
        this.title = title;
        this.option = option;
        this.registrationId = registrationId;
    }

    @Override
    public void process() {
        StudentService service = StudentService.getInstance();
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Are you sure you want to drop from course? (1/0) ");
            int confirm = sc.nextInt();
            if (confirm == 1) {
                service.withdrawFromCourse(registrationId);
                System.out.println("Withdrawn Successfully");
            }

        } catch (SQLException e) {
            System.out.println("Something went wrong");
        }
    }

    @Override
    public void show() throws RuntimeException {
        preScreenProcess();
        clearConsole();
        System.out.println("\n" + title + "\n");
        process();
        postScreenProcess();
    }
}