package database.models;

import java.time.LocalDate;

public class Student extends User {
    private Integer studentId;
    private String entryNo;
    private Batch batch;

    public Student(
            Integer id,
            String firstName,
            String lastName,
            String email,
            String phone,
            LocalDate joinDate,
            Integer studentId,
            String entryNo,
            Batch batch
    ) {
        super(id, firstName, lastName, email, phone, joinDate);
        this.studentId = studentId;
        this.entryNo = entryNo;
        this.batch = batch;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getEntryNo() {
        return entryNo;
    }

    public void setEntryNo(String entryNo) {
        this.entryNo = entryNo;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    @Override
    public String toString() {
        return "Student{" +
                "student_id=" + studentId +
                ", entryNo='" + entryNo + '\'' +
                ", batch=" + batch +
                "," + super.toString() + '}';
    }
}