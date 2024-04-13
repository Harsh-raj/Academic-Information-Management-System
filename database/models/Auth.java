package database.models;

public class Auth {
    private User user;
    private String password;

    public Auth(User user, String password) {
        this.user = user;
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Auth{" +
                "user=" + user +
                ", password='" + password + '\'' +
                '}';
    }
}