package database.models;

public class OfferType {

    private Batch batch;
    private Course course;
    private OfferTypeEnum type;

    public OfferType(Batch batch, Course course, OfferTypeEnum type) {
        this.batch = batch;
        this.course = course;
        this.type = type;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public OfferTypeEnum getType() {
        return type;
    }

    public void setType(OfferTypeEnum type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "OfferType{" +
                "batch=" + batch +
                ", course=" + course +
                ", type=" + type +
                '}';
    }
}