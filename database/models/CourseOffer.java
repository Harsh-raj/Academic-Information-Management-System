package database.models;

public class CourseOffer {
    private Integer offerId;
    private Course course;
    private Faculty faculty;
    private Session session;

    public CourseOffer(Integer offerId, Course course, Faculty faculty, Session session) {
        this.offerId = offerId;
        this.course = course;
        this.faculty = faculty;
        this.session = session;
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return "CourseOffer{" +
                "offerId=" + offerId +
                ", course=" + course +
                ", faculty=" + faculty +
                ", session=" + session +
                '}';
    }
}