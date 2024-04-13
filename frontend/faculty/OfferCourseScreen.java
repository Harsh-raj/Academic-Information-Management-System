package frontend.faculty;

import backend.FacultyService;
import database.models.Course;
import frontend.BackScreen;
import frontend.ProtectedScreen;

import java.sql.SQLException;
import java.util.*;

public class OfferCourseScreen extends ProtectedScreen {
    public OfferCourseScreen() {
        title = "Offer a Course";
        option = "Offer Course";
        backScreen = new BackScreen();
    }

    @Override
    public void process() {
        FacultyService service = FacultyService.getInstance();
        try {
            Scanner sc = new Scanner(System.in);
            List<Course> allCoursesForOffering = service.getAllUnofferedCourses();
            subScreens = new ArrayList<>();
            System.out.println("Available Courses: ");
            showCourses(allCoursesForOffering);
            System.out.print("Enter the choice of course that you want to offer: (Enter 0 to ignore) ");
            int choice = sc.nextInt();
            if(choice > 0)
            {
                service.offerCourse(allCoursesForOffering.get(choice - 1));
                System.out.println("Course Offer successfully");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
            }
        } catch (SQLException e) {
            System.out.println("Sorry! Some error occured.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
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
    private void showCourses(List<Course> courses) {
        Map<String, Integer> columnLengths = new HashMap<>();
        columnLengths.put("Code", 0);
        columnLengths.put("Title", 0);
        columnLengths.put("Credit", 0);

        courses.forEach(course -> {
            if(course.getCode().length() > columnLengths.get("Code"))
                columnLengths.put("Code", course.getCode().length());
        });
        courses.forEach(course -> {
            if(course.getTitle().length() > columnLengths.get("Title"))
                columnLengths.put("Title", course.getTitle().length());
        });
        courses.forEach(course -> {
            if(course.getCredit().length() > columnLengths.get("Credit"))
                columnLengths.put("Credit", course.getCredit().length());
        });


        String formatString = "%d. %-" + (columnLengths.get("Code")+2) + "s %-"+(columnLengths.get("Title")+2)+"s %-"+(columnLengths.get("Credit")+2)+"s\n";
        int index=0;
        for(Course course: courses)
            System.out.printf(formatString, ++index, course.getCode(), course.getTitle(), course.getCredit());
        System.out.println();
    }
}