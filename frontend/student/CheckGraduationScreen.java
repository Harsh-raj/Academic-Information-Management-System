package frontend.student;

import backend.StudentService;
import frontend.BackScreen;
import frontend.ProtectedScreen;

import java.sql.SQLException;

public class CheckGraduationScreen extends ProtectedScreen {
    public CheckGraduationScreen() {
        title = "Let's see if you are graduated";
        option = "Check Graduation";
        backScreen = new BackScreen();
    }

    @Override
    public void process() {
        StudentService service = StudentService.getInstance();
        try {
            boolean b = service.checkGraduation();
            if(b)
                System.out.println("Congratulations! You have Graduated!");
            else
                System.out.println("Sorry! You are yet to Graduate!");
        } catch (SQLException e) {
            System.out.println("Something went wrong");
        }
    }
}