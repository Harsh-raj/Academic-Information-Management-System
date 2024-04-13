package database.models;

import java.time.LocalDate;

public class AcademicOffice extends User {
    private Integer acadId;

    public AcademicOffice(
            Integer id,
            String firstName,
            String lastName,
            String email,
            String phone,
            LocalDate joinDate,
            Integer acadId
    ) {
        super(id, firstName, lastName, email, phone, joinDate);
        this.acadId = acadId;
    }

    public Integer getAcadId() {
        return acadId;
    }

    public void setAcadId(Integer acadId) {
        this.acadId = acadId;
    }

    @Override
    public String toString() {
        return "AcademicOffice{" +
                "acadId=" + acadId +
                "," + super.toString() + '}';
    }
}