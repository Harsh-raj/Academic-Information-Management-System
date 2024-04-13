package frontend.faculty;

import backend.FacultyService;
import database.models.MTPInfo;
import frontend.BackScreen;
import frontend.ProtectedScreen;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreditEntryScreen extends ProtectedScreen {

    public CreditEntryScreen() {
        title = "Enter Credit for an MTP";
        option = "Enter Credit for an MTP";
        backScreen = new BackScreen();
    }

    @Override
    public void process() {
        FacultyService service = FacultyService.getInstance();
        try {
            List<MTPInfo> mtps = service.getMTPInfo();
            subScreens = new ArrayList<>();
            for (MTPInfo mtp : mtps) {
                if (mtp.getStudent().getEntryNo() != null)
                    subScreens.add(new MTPCreditEntryScreen(mtp.getTitle(), mtp.getTitle(), mtp));
            }
        } catch (SQLException e) {
            System.out.println("Sorry! Some error occured");
        }
    }

}