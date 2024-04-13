package frontend;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Screen {

    protected String title;
    protected String option;
    protected List<Screen> subScreens;
    protected Screen backScreen;

    public Screen() {
        title = "";
        option = "";
        subScreens = new ArrayList<>();
        // Don't add back screen otherwise infinite recursion
    }

    public Screen(String title, String option) {
        this();
        this.title = title;
        this.option = option;
    }

    public void process() {
    }

    public void preScreenProcess() {
    }

    public void postScreenProcess() {
    }

    public void show() throws RuntimeException {
        preScreenProcess();
        int choice = 1;
        do {
            clearConsole();
            System.out.println("\n" + title + "\n");
            process();
            for (int i = 1; i <= subScreens.size(); i++)
                System.out.println(i + ". " + subScreens.get(i - 1).getOption());
            System.out.println("0. " + backScreen.getOption());
            System.out.print("Enter your choice: ");
            Scanner sc = new Scanner(System.in);
            choice = sc.nextInt();

            if (choice > 0 && choice <= subScreens.size()) {
                try {
                    subScreens.get(choice - 1).show();
                } catch (Exception ignored) {
                }
            } else if (choice < 0 || choice > subScreens.size()) {
                System.out.println("Invalid Option!");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
            }

        } while (choice != 0);
        postScreenProcess();
    }

    public void clearConsole() {
        try {
            String operatingSystem = System.getProperty("os.name"); //Check the current operating system

            if (operatingSystem.contains("Windows")) {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}