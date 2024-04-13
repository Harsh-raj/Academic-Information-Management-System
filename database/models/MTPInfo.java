package database.models;

public class MTPInfo {
    private Integer id;
    private Student student;
    private Faculty faculty;
    private String title;
    private String domains;
    private Integer credits;

    public MTPInfo(
            Integer id,
            Student student,
            Faculty faculty,
            String title,
            String domains,
            Integer credits
    ) {
        this.id = id;
        this.student = student;
        this.faculty = faculty;
        this.title = title;
        this.domains = domains;
        this.credits = credits;
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

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDomains() {
        return domains;
    }

    public void setDomains(String domains) {
        this.domains = domains;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return "MTPInfo{" +
                "id=" + id +
                ", student=" + student +
                ", faculty=" + faculty +
                ", title='" + title + '\'' +
                ", domains='" + domains + '\'' +
                ", credits=" + credits +
                '}';
    }
}