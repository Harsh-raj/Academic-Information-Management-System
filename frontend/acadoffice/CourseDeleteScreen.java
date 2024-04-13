package frontend.acadoffice;

import backend.AcadOfficeService;
import frontend.BackScreen;
import frontend.ProtectedScreen;

import java.sql.SQLException;
import java.util.Scanner;

public class CourseDeleteScreen extends ProtectedScreen {
    public CourseDeleteScreen() {
        title = "Delete a course";
        option = "Delete Course";
        backScreen = new BackScreen();
    }

    @Override
    public void process() {
        AcadOfficeService service = AcadOfficeService.getInstance();

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter course code of the course that you want to delete: ");
        String courseCode = sc.nextLine().strip();

        try {
            service.deleteCourse(courseCode);
            System.out.println("Course Deleted Successfully!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        } catch (SQLException e) {
            System.out.println("Sorry! Something went wrong");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
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
}