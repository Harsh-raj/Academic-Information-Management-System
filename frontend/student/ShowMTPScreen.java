package frontend.student;

import backend.StudentService;
import database.models.MTPInfo;
import frontend.BackScreen;
import frontend.ProtectedScreen;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowMTPScreen extends ProtectedScreen {
    public ShowMTPScreen() {
        title = "Show/Register for an MTP";
        option = "Show/Register for MTP";
        backScreen = new BackScreen();
    }

    @Override
    public void process() {
        StudentService service = StudentService.getInstance();
        subScreens = new ArrayList<>();
        try {
            MTPInfo registeredMTP = service.getRegisteredMTP();
            if (registeredMTP == null) {
                System.out.println("You are not registered to any MTP. Please register for one: ");
                List<MTPInfo> offeredMTPs = service.getOfferedMTPs();
                showMtps(offeredMTPs);
            } else {
                System.out.println("Registered to: ");
                System.out.println("Title: " + registeredMTP.getTitle());
                System.out.println("Faculty: " + registeredMTP.getFaculty().getFirstName() + " " + registeredMTP.getFaculty().getLastName());
                System.out.println("Domains: " + registeredMTP.getDomains());
                System.out.println("Credits: " + registeredMTP.getCredits());
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong");
        }

    }

    private void showMtps(List<MTPInfo> mtps) {
        Map<String, Integer> columnLengths = new HashMap<>();
        columnLengths.put("Title", 7);
        columnLengths.put("Faculty", 9);
        columnLengths.put("Domains", 9);

        mtps.forEach(mtp -> {
            if (mtp.getTitle().length() > columnLengths.get("Title"))
                columnLengths.put("Title", mtp.getTitle().length());
        });
        mtps.forEach(mtp -> {
            if (mtp.getFaculty().getFirstName().length() + mtp.getFaculty().getLastName().length() > columnLengths.get("Faculty"))
                columnLengths.put("Faculty", mtp.getFaculty().getFirstName().length() + mtp.getFaculty().getLastName().length());
        });
        mtps.forEach(mtp -> {
            if (mtp.getDomains().length() > columnLengths.get("Domains"))
                columnLengths.put("Domains", mtp.getDomains().length());
        });

        String formatString = " %-" + (columnLengths.get("Title") + 2) + "s %-" + (columnLengths.get("Faculty") + 3) + "s %-" + (columnLengths.get("Domains") + 2) + "s";
        System.out.printf("\t" + formatString + "\n", "Title", "Faculty", "Domains");
        mtps.forEach(mtp -> {
            String option = String.format(formatString, mtp.getTitle(), mtp.getFaculty().getFirstName() + " " + mtp.getFaculty().getLastName(), mtp.getDomains());
            subScreens.add(new MTPRegistrationConfirmScreen(mtp.getTitle(), option, mtp));
        });
    }
}