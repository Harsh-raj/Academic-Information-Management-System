package backend;

import database.models.*;
import database.repositories.StudentRepo;
import frontend.LoggedInUser;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentService {
    private static StudentService instance;
    private StudentRepo studentRepo;
    public static final Integer MIN_ELECTIVES = 2;

    private StudentService() {
        studentRepo = new StudentRepo();
    }

    public static StudentService getInstance() {
        if (instance == null)
            instance = new StudentService();
        return instance;
    }

    public Optional<Student> getStudentDetails(Integer id) throws SQLException {
        // Fetch the details of the student with this id
        return studentRepo.getStudent(id);
    }

    public List<CourseRegister> getGrade(Integer studentId) throws SQLException {
        // Return the list of registered Courses and their grades and statuses
        return studentRepo.getGrade(studentId);
    }

    public List<CourseOffer> getAllOfferedCourses() throws SQLException {
        // Return the list of all offered courses for the current session
        return studentRepo.getAllOfferedCourses(LoggedInUser.getInstance().getId());
    }

    public void registerforOffer(CourseOffer co) throws SQLException {
        // Register the current logged in student to the offered course if constraints permit. Throw SQLException if couldn't register
        // Check avg credits for prev 2 sems
        Integer avgCreditForLast2Sems = studentRepo.getAvgCreditForLast2Sems(LoggedInUser.getInstance().getId());
        int registeredCredits = 0;
        List<CourseRegister> registeredCourses = getRegisteredCourses();
        for (CourseRegister c : registeredCourses)
            registeredCredits += Integer.parseInt(String.valueOf(c.getOffer().getCourse().getCredit().charAt(c.getOffer().getCourse().getCredit().length() - 1)));
        registeredCredits += Integer.parseInt(String.valueOf(co.getCourse().getCredit().charAt(co.getCourse().getCredit().length() - 1)));

        if (registeredCredits > 1.25 * avgCreditForLast2Sems) {
            System.out.println("Credit Limit Reached!");
            throw new SQLException();
        }

        // Find courses done by student and check prereqs
        List<CourseRegister> allSessionRegisteredCourses = studentRepo.getAllSessionRegisteredCourses(LoggedInUser.getInstance().getId());
        for (Course prereq : co.getCourse().getPrerequisites()) {
            List<CourseRegister> courseRegisters = allSessionRegisteredCourses.stream().filter(c -> Objects.equals(c.getOffer().getCourse().getId(), prereq.getId())).collect(Collectors.toList());
            if (courseRegisters.size() < 1) {
                System.out.println("Prerequisite not fulfilled: " + prereq.getCode() + " - " + prereq.getTitle());
                throw new SQLException();
            }
        }

        // Register to offer
        studentRepo.registerStudent(co.getOfferId(), LoggedInUser.getInstance().getId());
    }

    public List<CourseRegister> getRegisteredCourses() throws SQLException {
        // Return the list of all registed courses for the current session
        return studentRepo.getRegisteredCourses(LoggedInUser.getInstance().getId());
    }

    public void withdrawFromCourse(Integer registrationId) throws SQLException {
        // Set the status of the course registration to DROPPED. Throw SQLException if couldn't drop
        studentRepo.withdrawFromCourse(registrationId);
    }

    public boolean checkGraduation() throws SQLException {
        // Check whether the student has graduated or not.

        // Check Passed in all failed courses
        List<Integer> failedCourses = studentRepo.getFailedCourses(LoggedInUser.getInstance().getId());
        for (Integer failedCourse : failedCourses)
            if (!studentRepo.checkPassedCourse(LoggedInUser.getInstance().getId(), failedCourse))
                return false;

        // Check Completed All Core Courses
        List<Integer> allCoreCourses = studentRepo.getAllCoreCourses(LoggedInUser.getInstance().getId());
        List<Integer> allCompletedCourses = studentRepo.getAllCompletedCourses(LoggedInUser.getInstance().getId());

        for (Integer coreCourse : allCoreCourses) {
            boolean found = false;
            for (Integer completedCourse : allCompletedCourses) {
                if (Objects.equals(completedCourse, coreCourse))
                    found = true;
            }
            if (!found)
                return false;
        }

        // Check minimum number of electives
        List<Integer> electiveCourses = studentRepo.getAllElectiveCourses(LoggedInUser.getInstance().getId());
        if (electiveCourses.size() < MIN_ELECTIVES)
            return false;

        // Check MTP Credits
        Optional<Integer> mtp = studentRepo.getMtp(LoggedInUser.getInstance().getId());
        if (mtp.isEmpty() || mtp.get() == 0)
            return false;

        return true;
    }

    public MTPInfo getRegisteredMTP() throws SQLException {
        // Return the MTPInfo of the student if he is already reistered to that MTP. Otherwise return null
        return studentRepo.getRegisteredMTP(LoggedInUser.getInstance().getId());
    }

    public List<MTPInfo> getOfferedMTPs() throws SQLException {
        // Return all MTPs which are offered but does not have any students working on it
        return studentRepo.getOfferedMTPs();
    }

    public void registerForMtp(Integer mtpId) throws SQLException {
        // Register this student in the MTP with mtpId. Throw SQLException if couldn't register
        studentRepo.registerForMtp(mtpId, LoggedInUser.getInstance().getId());
    }

    public Course getCourseDetails(Integer courseId) throws SQLException {
        return FacultyService.getInstance().getFacultyRepo().getCourseDetails(courseId);
    }

    public StudentRepo getStudentRepo() {
        return studentRepo;
    }

    public List<Session> getSessions(Integer studentId) throws SQLException {
        return studentRepo.getSession(studentId);
    }
}