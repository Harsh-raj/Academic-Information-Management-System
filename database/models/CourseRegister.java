package database.models;

public class CourseRegister {
    private Integer id;
    private Student student;
    private CourseOffer offer;
    private Integer grade;
    private Integer creditsReceived;
    private CourseRegistrationStatus status;

    public CourseRegister(
            Integer id,
            Student student,
            CourseOffer offer,
            Integer grade,
            Integer creditsReceived,
            CourseRegistrationStatus status
    ) {
        this.id = id;
        this.student = student;
        this.offer = offer;
        this.grade = grade;
        this.creditsReceived = creditsReceived;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public CourseOffer getOffer() {
        return offer;
    }

    public void setOffer(CourseOffer offer) {
        this.offer = offer;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getCreditsReceived() {
        return creditsReceived;
    }

    public void setCreditsReceived(Integer creditsReceived) {
        this.creditsReceived = creditsReceived;
    }

    public CourseRegistrationStatus getStatus() {
        return status;
    }

    public void setStatus(CourseRegistrationStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CourseRegister{" +
                "id=" + id +
                ", student=" + student +
                ", offer=" + offer +
                ", grade=" + grade +
                ", creditsReceived=" + creditsReceived +
                ", status=" + status +
                '}';
    }
}