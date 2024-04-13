package backend;

import database.models.Course;
import database.models.CourseRegister;
import database.models.Session;
import database.models.Student;
import database.repositories.AcadOfficeRepo;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AcadOfficeService {
    AcadOfficeRepo acadOfficeRepo;
    private static AcadOfficeService instance;

    private AcadOfficeService() {
        acadOfficeRepo = new AcadOfficeRepo();
    }

    public static AcadOfficeService getInstance() {
        if (instance == null)
            instance = new AcadOfficeService();
        return instance;
    }

    public List<Course> getCourses() throws SQLException {
        // Fetch all the courses from database and return them
        List<Course> course = acadOfficeRepo.getCourses();
        return course;
    }

    public Optional<Course> getCourse(String courseCode) throws SQLException {
        // Fetch the course
        return acadOfficeRepo.getCourse(courseCode);
    }

    public Optional<Student> getStudentDetails(String entry) throws SQLException {
        // Fetch the details of the student with this id
        return acadOfficeRepo.getStudent(entry);
    }

    public List<Session> getSessions(Integer id) throws SQLException {
        return StudentService.getInstance().getSessions(id);
    }

    public List<CourseRegister> getGrade(Integer id) throws SQLException {

        return StudentService.getInstance().getGrade(id);
    }


    public void addCourse(Course course) throws SQLException {
        acadOfficeRepo.addCourse(course);
    }

    public void editCourse(Course course) throws SQLException {
        acadOfficeRepo.editCourseDetails(course);
        acadOfficeRepo.deletePrerequisites(course.getId());
        acadOfficeRepo.addPrerequisites(course);
    }

    public void deleteCourse(String courseCode) throws SQLException {
        acadOfficeRepo.deleteCourse(courseCode);
    }
}