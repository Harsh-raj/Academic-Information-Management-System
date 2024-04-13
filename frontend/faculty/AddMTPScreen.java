package frontend.faculty;

import backend.FacultyService;
import database.models.MTPInfo;
import frontend.BackScreen;
import frontend.ProtectedScreen;

import java.sql.SQLException;
import java.util.Scanner;

public class AddMTPScreen extends ProtectedScreen {
    public AddMTPScreen() {
        title = "Offer a MTP";
        option = "Offer New MTP";
        backScreen = new BackScreen();
    }

    @Override
    public void process() {
        Scanner sc = new Scanner(System.in);
        FacultyService service = FacultyService.getInstance();
        System.out.println("Enter MTP details:-");

        try {
            System.out.print("Enter title of MTP (Press ENTER to go back): ");
            String title = sc.nextLine().strip();
            if(title.equals("")){
                return;
            }
            System.out.print("Enter required domains of the MTP: ");
            String domains = sc.nextLine().strip();
            service.addMTP(new MTPInfo(null,null,null,title,domains, null));
            System.out.println("MTP added successfully!");
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