package frontend.faculty;

import backend.AuthService;
import frontend.*;

import java.sql.SQLException;

public class FacultyScreen extends Screen {
    public FacultyScreen() {
        title = "Welcome Dr. ";
        option = "Faculty";
        subScreens.add(new OfferCourseScreen());
        subScreens.add(new ViewOfferedCoursesScreen());
        subScreens.add(new MTPDetailsScreen());
        backScreen = new BackScreen("", "Logout");
    }

    @Override
    public void preScreenProcess() {
        new AuthScreen(UserType.FACULTY).show();
        title = "Welcome Dr. " + LoggedInUser.getInstance().getName();
    }

    @Override
    public void postScreenProcess() {
        try {
            AuthService.getInstance().logoutUser();
        } catch (SQLException ignored) {
        }
    }
}