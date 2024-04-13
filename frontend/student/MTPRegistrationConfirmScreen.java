package frontend.student;

import backend.StudentService;
import database.models.MTPInfo;
import frontend.ProtectedScreen;

import java.sql.SQLException;
import java.util.Scanner;

public class MTPRegistrationConfirmScreen extends ProtectedScreen {
    private MTPInfo mtp;

    public MTPRegistrationConfirmScreen(String title, String option, MTPInfo mtp) {
        this.title = title;
        this.option = option;
        this.mtp = mtp;
    }

    @Override
    public void process() {
        Scanner sc = new Scanner(System.in);
        StudentService service = StudentService.getInstance();
        System.out.println("Registering for: ");
        System.out.println("Title: " + mtp.getTitle());
        System.out.println("Faculty: " + mtp.getFaculty().getFirstName() + " " + mtp.getFaculty().getLastName());
        System.out.println("Domains: " + mtp.getDomains());
        System.out.println();
        System.out.print("Do you really want to register for this MTP? (1/0):");
        int confirm = sc.nextInt();
        if (confirm == 1) {
            try {
                service.registerForMtp(mtp.getId());
                System.out.println("Registered for MTP Successfully");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            } catch (SQLException e) {
                System.out.println("Something went wrong");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
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