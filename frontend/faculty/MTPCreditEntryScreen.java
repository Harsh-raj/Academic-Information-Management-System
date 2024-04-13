package frontend.faculty;

import backend.FacultyService;
import database.models.MTPInfo;
import frontend.ProtectedScreen;

import java.sql.SQLException;
import java.util.Scanner;

public class MTPCreditEntryScreen extends ProtectedScreen {
    MTPInfo mtp;
    public MTPCreditEntryScreen(String title, String option, MTPInfo mtp) {
        this.title = title;
        this.option = option;
        this.mtp = mtp;
    }

    @Override
    public void process() {
        Scanner sc = new Scanner(System.in);
        FacultyService service = FacultyService.getInstance();
        try {
            System.out.println("Student Working: ");
            System.out.println("Entry: "+mtp.getStudent().getEntryNo());
            System.out.println("Name: "+ mtp.getStudent().getFirstName()+" "+mtp.getStudent().getLastName());
            System.out.print("Enter the credits for the current student working on it: ");
            Integer credit = sc.nextInt();
            service.addMTPCredit(mtp.getId(), credit);
            System.out.println("Credits entered successfully");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        } catch (SQLException e) {
            System.out.println("Sorry! Something went wrong");
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
}