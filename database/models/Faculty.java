package database.models;

import java.time.LocalDate;

public class Faculty extends User {

    private Integer facultyId;

    public Faculty(
            Integer id,
            String firstName,
            String lastName,
            String email,
            String phone,
            LocalDate joinDate,
            Integer facultyId
    ) {
        super(id, firstName, lastName, email, phone, joinDate);
        this.facultyId = facultyId;
    }

    public Integer getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "facultyId=" + facultyId +
                "," + super.toString() + '}';
    }
}