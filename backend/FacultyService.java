package backend;

import database.models.Course;
import database.models.CourseRegister;
import database.models.MTPInfo;
import database.repositories.FacultyRepo;
import frontend.LoggedInUser;

import java.sql.SQLException;
import java.util.List;

public class FacultyService {
    private static FacultyService instance;
    private FacultyRepo facultyRepo;

    private FacultyService()
    {
        facultyRepo = new FacultyRepo();
    }

    public static FacultyService getInstance()
    {
        if(instance==null)
            instance = new FacultyService();
        return instance;
    }

    public List<Course> getAllOfferedCourses() throws SQLException {
        // Fetch all courses that this faculty is currently offering
        return facultyRepo.getAllOfferedCourses(LoggedInUser.getInstance().getId());
    }

    public List<Course> getAllUnofferedCourses() throws SQLException {
        // Fetch all active courses available for offering in this current session
        return facultyRepo.getAllUnofferedCourses();
    }

    public List<CourseRegister> getCourseDetails(Integer courseId) throws SQLException {
        // Fetch the details of the course and its registered students
        Course course = facultyRepo.getCourseDetails(courseId);
        return facultyRepo.getCourseRegisterForCourse(course, LoggedInUser.getInstance().getId());
    }

    public void gradeEntry(String student_entry_no, Integer grade, Integer courseOfferId) throws SQLException {
        // Enter the grade for student, throw SQLException if grade could not be entered
        facultyRepo.gradeEntry(student_entry_no, grade, courseOfferId);
    }

    public List<MTPInfo> getMTPInfo() throws SQLException {
        // Fetch all the MTPs that the Faculty is offering. Use LEFT JOIN because of nullable student id FK
        return facultyRepo.getMTPInfo(LoggedInUser.getInstance().getId());
    }

    public void addMTP(MTPInfo mtpInfo) throws SQLException {
        // Add MTP without student.
        facultyRepo.addMTP(mtpInfo, LoggedInUser.getInstance().getId());
    }

    public void addMTPCredit(Integer mtpId, Integer credit) throws  SQLException {
        // Update the credit of the mtp
        facultyRepo.addMTPCredit(mtpId, credit);
    }

    public void offerCourse(Course course) throws SQLException {
        // Add the offer for this course
        facultyRepo.offerCourse(course, LoggedInUser.getInstance().getId());
    }

    public FacultyRepo getFacultyRepo() {
        return facultyRepo;
    }
}