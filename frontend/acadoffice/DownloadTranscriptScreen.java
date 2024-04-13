package frontend.acadoffice;

import frontend.BackScreen;
import frontend.MessagePasser;
import frontend.ProtectedScreen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DownloadTranscriptScreen extends ProtectedScreen {

    public DownloadTranscriptScreen() {
        title = "Download Transcript";
        option = "Download Transcript";
        backScreen = new BackScreen();
    }

    @Override
    public void process() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Path to download file: ");
        String path = sc.nextLine().strip();
        String transcript = (String) MessagePasser.getInstance().getMessages().get("Transcript");
        String entry = (String) MessagePasser.getInstance().getMessages().get("Entry");

        File dir = new File(path);
        if (!dir.exists()) dir.mkdirs();
        try {
            FileWriter fileWriter = new FileWriter(new File(path, entry + ".txt"));
            fileWriter.write(transcript);
            fileWriter.close();
            System.out.println("File Downloaded");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        } catch (IOException e) {

            System.out.println("Error while writing file");
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