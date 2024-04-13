package frontend.student;

import backend.StudentService;
import database.models.Course;
import database.models.CourseOffer;
import frontend.ProtectedScreen;

import java.sql.SQLException;
import java.util.Scanner;

public class CourseRegisterConfirmScreen extends ProtectedScreen {
    private Integer offerId;
    private CourseOffer co;

    public CourseRegisterConfirmScreen(String title, String option, Integer offerId, CourseOffer co) {
        this.title = title;
        this.option = option;
        this.offerId = offerId;
        this.co = co;
    }

    @Override
    public void process() {
        StudentService service = StudentService.getInstance();
        try {
            Course course = service.getCourseDetails(co.getCourse().getId());
            co.setCourse(course);
            System.out.println("Faculty: " + co.getFaculty().getFirstName() + " " + co.getFaculty().getLastName());
            System.out.println("L-T-P-S-C: " + course.getCredit());
            System.out.println("\nDescription:-\n" + course.getDescription());
            System.out.println("\nPrerequisites:-");

            for (Course cp : course.getPrerequisites())
                System.out.println("* " + cp.getCode() + " - " + cp.getTitle());
            System.out.println();
            Scanner sc = new Scanner(System.in);
            System.out.print("Do you really want to register for this course? (1/0): ");
            int confirm = sc.nextInt();
            if (confirm == 1) {
                service.registerforOffer(co);
                System.out.println("Registered Successfully");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            try {
                Thread.sleep(2000);
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
}