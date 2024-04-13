package frontend;

public class LoggedInUser {
    private static LoggedInUser instance;
    private String name;
    private String email;
    private Integer userId;
    private Integer id;

    private LoggedInUser() {
        userId = null;
        id = null;
        name = null;
        email = null;
    }

    public static LoggedInUser getInstance() {
        if (instance == null)
            instance = new LoggedInUser();
        return instance;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}