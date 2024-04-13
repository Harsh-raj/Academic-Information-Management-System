package frontend.faculty;

import backend.FacultyService;
import database.models.MTPInfo;
import frontend.BackScreen;
import frontend.ProtectedScreen;

import java.sql.SQLException;
import java.util.List;

public class MTPDetailsScreen extends ProtectedScreen {
    public MTPDetailsScreen() {
        title = "Master Thesis Projects";
        option = "MTP Details";
        subScreens.add(new AddMTPScreen());
        subScreens.add(new CreditEntryScreen());
        backScreen = new BackScreen();
    }

    @Override
    public void process() {
        FacultyService service = FacultyService.getInstance();
        try {
            List<MTPInfo> mtps = service.getMTPInfo();
            System.out.println("Available MTPs:-");
            for (MTPInfo mtp : mtps) {
                System.out.println("* " + mtp.getTitle() + "\tDomains: " + mtp.getDomains());
                if (mtp.getStudent().getEntryNo() != null)
                    System.out.println("\tEntry: " + mtp.getStudent().getEntryNo() + " Name: " + mtp.getStudent().getFirstName() + " " + mtp.getStudent().getLastName() + " Credits: " + mtp.getCredits());
            }
            System.out.println();
        } catch (SQLException e) {
            System.out.println("Sorry! Some error occured");
        }
    }
}