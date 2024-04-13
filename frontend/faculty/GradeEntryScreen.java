package frontend.faculty;

import backend.FacultyService;
import database.models.CourseOffer;
import frontend.BackScreen;
import frontend.ProtectedScreen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GradeEntryScreen extends ProtectedScreen {
    private CourseOffer courseOffer;

    public GradeEntryScreen(CourseOffer courseOffer) {
        this.title = "Perform Grade Entry";
        option = "Grade Entry";
        this.courseOffer = courseOffer;
        backScreen = new BackScreen();
    }

    @Override
    public void process() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Enter path of the csv file: ");
            String path = sc.nextLine().strip();
            parseGradeCsv(path);
            System.out.println("Grade Entry Complete");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        } catch (SQLException e) {
            System.out.println("Some error occured!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {

            }
        }
    }

    void parseGradeCsv(String path) throws SQLException, IOException {
        FacultyService service = FacultyService.getInstance();
        BufferedReader csvReader = new BufferedReader(new FileReader(path));
        String headerRow = csvReader.readLine(); //discarded header row
        String row;
        while ((row = csvReader.readLine()) != null) {
            List<String> recordData = Arrays.asList(row.split(","));
            service.gradeEntry(recordData.get(0), Integer.parseInt(recordData.get(1)), courseOffer.getOfferId());
        }
    }
}