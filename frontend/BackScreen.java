package frontend;

public class BackScreen extends Screen {
    public BackScreen() {
        title = "";
        option = "Back";
    }

    public BackScreen(String title, String option) {
        this.title = title;
        this.option = option;
    }
}