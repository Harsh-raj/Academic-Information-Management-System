package database.models;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private Integer id;
    private String code;
    private String title;
    private String description;
    private String credit;
    private List<Course> prerequisites;

    public Course(
            Integer id,
            String code,
            String title,
            String description,
            String credit
    ) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.description = description;
        this.credit = credit;
        prerequisites = new ArrayList<>();
    }

    public Course(
            Integer id,
            String code,
            String title,
            String description,
            String credit,
            List<Course> prerequisites
    ) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.description = description;
        this.credit = credit;
        this.prerequisites = prerequisites;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public List<Course> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", credit='" + credit + '\'' +
                '}';
    }
}