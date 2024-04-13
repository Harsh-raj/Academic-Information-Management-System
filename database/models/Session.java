package database.models;

import java.util.Objects;

public class Session {
    private Integer id;
    private Integer sem;
    private Integer year;
    private SessionStatus status;

    public Session(Integer id, Integer sem, Integer year, SessionStatus status) {
        this.id = id;
        this.sem = sem;
        this.year = year;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSem() {
        return sem;
    }

    public void setSem(Integer sem) {
        this.sem = sem;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public void setStatus(SessionStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", sem=" + sem +
                ", year=" + year +
                ", status=" + status +
                '}';
    }
    public boolean equals(Session s)
    {
        return Objects.equals(this.sem, s.getSem()) && Objects.equals(this.year, s.getYear());
    }

}