package frontend.acadoffice;

import backend.AcadOfficeService;
import database.models.Course;
import frontend.BackScreen;
import frontend.ProtectedScreen;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class CourseEditScreen extends ProtectedScreen {
    public CourseEditScreen() {
        title = "Edit a Course";
        option = "Edit Course";
        backScreen = new BackScreen();
    }

    @Override
    public void process() {
        AcadOfficeService service = AcadOfficeService.getInstance();
        System.out.println("Enter course details:-");

        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter course code you want to edit: ");
            String courseCode = sc.nextLine().strip();
            Optional<Course> courseOptional = service.getCourse(courseCode);
            if (courseOptional.isEmpty()) {
                System.out.println("No such course found!");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
            } else {
                Course course = courseOptional.get();
                System.out.print("Enter New Course Code (Press ENTER to ignore): ");
                String code = sc.nextLine().strip();
                if (!code.equals(""))
                    course.setCode(code);

                System.out.print("Enter New Title (Press ENTER to ignore): ");
                String title = sc.nextLine().strip();
                if (!title.equals(""))
                    course.setTitle(title);

                System.out.print("Enter New Description (Press ENTER to ignore): ");
                String description = sc.nextLine().strip();
                if (!description.equals(""))
                    course.setDescription(description);

                System.out.print("Enter New L-T-P-S-C (Press ENTER to ignore): ");
                String ltpsc = sc.nextLine().strip();
                if (!ltpsc.equals(""))
                    course.setCredit(ltpsc);

                System.out.print("Enter the Course Code of the Prerequisites you want to ADD (Comma Separated) (Press ENTER to ignore): ");
                String[] codes = sc.nextLine().strip().split(",");
                for (String code1 : codes) {
                    if (!code1.strip().equals(""))
                        course.getPrerequisites().add(new Course(null, code1.strip(), null, null, null));
                }

                System.out.print("Enter the Course Code of the Prerequisites you want to DELETE (Comma Separated) (Press ENTER to ignore): ");
                codes = sc.nextLine().strip().split(",");
                for (String code1 : codes)
                    course.getPrerequisites().removeIf(course1 -> !code1.strip().equals("") && course1.getCode().equals(code1.strip()));

                service.editCourse(course);
                System.out.println("Course Updated Successfully!");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
            }
        } catch (SQLException e) {
            System.out.println("Could not edit course! Something went wrong");
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