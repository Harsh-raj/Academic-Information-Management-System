package database.models;

public class Batch {
    private Integer id;
    private Session startSession;
    private Session endSession;
    private Integer duration;

    public Batch(
            Integer id,
            Session startSession,
            Session endSession,
            Integer duration
    ) {
        this.id = id;
        this.startSession = startSession;
        this.endSession = endSession;
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Session getStartSession() {
        return startSession;
    }

    public void setStartSession(Session startSession) {
        this.startSession = startSession;
    }

    public Session getEndSession() {
        return endSession;
    }

    public void setEndSession(Session endSession) {
        this.endSession = endSession;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Batch{" +
                "id=" + id +
                ", startSession=" + startSession +
                ", endSession=" + endSession +
                ", duration=" + duration +
                '}';
    }
}