package frontend.student;

import backend.AuthService;
import frontend.*;

import java.sql.SQLException;

public class StudentScreen extends Screen {

    public StudentScreen() {
        title = "Welcome Mr/Ms. ";
        option = "Student";
        subScreens.add(new ViewGradeScreen());
        subScreens.add(new CourseRegistrationScreen());
        subScreens.add(new WithdrawCourseScreen());
        subScreens.add(new CheckGraduationScreen());
        subScreens.add(new ShowMTPScreen());
        backScreen = new BackScreen("", "Logout");
    }

    @Override
    public void preScreenProcess() {
        new AuthScreen(UserType.STUDENT).show();
        title = "Welcome Mr/Ms. " + LoggedInUser.getInstance().getName();
    }

    @Override
    public void postScreenProcess() {
        try {
            AuthService.getInstance().logoutUser();
        } catch (SQLException ignored) {
        }
    }
}