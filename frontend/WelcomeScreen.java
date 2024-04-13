package frontend;

import frontend.acadoffice.AcadOfficeScreen;
import frontend.faculty.FacultyScreen;
import frontend.student.StudentScreen;

public class WelcomeScreen extends Screen {
    public WelcomeScreen() {
        title = "Welcome to AIMS Portal";
        subScreens.add(new StudentScreen());
        subScreens.add(new FacultyScreen());
        subScreens.add(new AcadOfficeScreen());
        backScreen = new BackScreen("", "Exit");
    }
}