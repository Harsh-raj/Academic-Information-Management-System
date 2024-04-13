package frontend.acadoffice;

import backend.AcadOfficeService;
import database.models.Course;
import frontend.BackScreen;
import frontend.ProtectedScreen;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CourseAddScreen extends ProtectedScreen {
    public CourseAddScreen() {
        title = "Add a Course";
        option = "Add Course";
        backScreen = new BackScreen();
    }

    @Override
    public void process() {
        Scanner sc = new Scanner(System.in);
        AcadOfficeService service = AcadOfficeService.getInstance();
        System.out.println("Enter course details:-");
        try {
            System.out.print("Enter Course Code: ");
            String code = sc.nextLine().strip();
            System.out.print("Enter Title of the Course: ");
            String title = sc.nextLine().strip();
            System.out.print("Enter Description of the Course: ");
            String description = sc.nextLine().strip();
            System.out.print("Enter L-T-P-S-C: ");
            String credits = sc.nextLine().strip();
            Course course = new Course(null, code, title, description, credits);

            System.out.print("Enter Prerequisite Course Codes (comma separated): ");
            String[] prerequisites = sc.nextLine().strip().split(",");
            List<Course> prereq = new ArrayList<>();
            for(String p: prerequisites)
                prereq.add(new Course(null, p.strip(), null, null, null));
            course.setPrerequisites(prereq);
            service.addCourse(course);
        } catch (SQLException e) {
            System.out.println("Could not add course! Something went wrong");
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