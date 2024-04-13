package database.models;

import java.time.LocalDateTime;

public class LoginSession {
    private Integer id;
    private User user;
    private LocalDateTime timestamp;

    public LoginSession(Integer id, User user, LocalDateTime timestamp) {
        this.id = id;
        this.user = user;
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "LoginSession{" +
                "Id=" + id +
                ", user=" + user +
                ", timestamp=" + timestamp +
                '}';
    }
}