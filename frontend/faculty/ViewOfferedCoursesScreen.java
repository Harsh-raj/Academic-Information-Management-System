package frontend.faculty;

import backend.FacultyService;
import database.models.Course;
import frontend.BackScreen;
import frontend.ProtectedScreen;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewOfferedCoursesScreen extends ProtectedScreen {
    public ViewOfferedCoursesScreen() {
        title = "Offered Courses";
        option = "View Offered courses and their details";
        backScreen = new BackScreen();
    }

    @Override
    public void process() {
        FacultyService service = FacultyService.getInstance();
        try {
            List<Course> allOfferedCourses = service.getAllOfferedCourses();
            subScreens = new ArrayList<>();
            System.out.println("Offered Courses: (Select option to see details)");
            for(Course c: allOfferedCourses)
                subScreens.add(new OfferedCourseDetailsScreen(String.format("%s - %s", c.getCode(), c.getTitle()), String.format("%s - %s", c.getCode(), c.getTitle()), c));
        } catch (SQLException e) {
            System.out.println("Sorry! Some error occured");
        }
    }
}