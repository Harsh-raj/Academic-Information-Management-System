package frontend.student;

import backend.StudentService;
import database.models.CourseOffer;
import frontend.BackScreen;
import frontend.ProtectedScreen;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseRegistrationScreen extends ProtectedScreen {
    public CourseRegistrationScreen() {
        title = "Course Registration";
        option = "Register for a Course";
        backScreen = new BackScreen();
    }

    @Override
    public void process() {
        StudentService service = StudentService.getInstance();

        try {
            List<CourseOffer> offeredCourses = service.getAllOfferedCourses();
            subScreens = new ArrayList<>();
            System.out.println("Available Courses for Registration: ");
            for(CourseOffer c : offeredCourses)
            {
                String option = String.format("%s\t%-35s\t%s\t%s %s", c.getCourse().getCode(), c.getCourse().getTitle(), c.getCourse().getCredit(), c.getFaculty().getFirstName(), c.getFaculty().getLastName());
                subScreens.add(new CourseRegisterConfirmScreen(c.getCourse().getCode()+" - "+c.getCourse().getTitle(), option, c.getOfferId(), c));
            }

        } catch (SQLException e) {
            System.out.println("Something went wrong");
        }

    }
}