package database.repositories;

import database.models.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepo extends Repository {

    public List<CourseRegister> getRegisteredCourses(Integer studentId) throws SQLException {
        ResultSet resultSet = conn.createStatement().executeQuery("select cr.id as crid, student_id, offer_id, grade, creditsreceived, cr.status as status, c.id as cid, c.title as title, c.credit as credit from ((courseregister cr inner join courseoffer co on cr.offer_id = co.id) inner join session s on co.session_id = s.id) inner join course c on c.id=co.course_id where s.status='ACTIVE' and cr.student_id = " + studentId + " and cr.status = 'ENROLLED'");
        List<CourseRegister> courseRegisters = new ArrayList<>();
        while (resultSet.next()) {
            courseRegisters.add(
                    new CourseRegister(resultSet.getInt("crid"), new Student(
                            resultSet.getInt("student_id"),
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null
                    ),
                            new CourseOffer(resultSet.getInt("offer_id"), new Course(resultSet.getInt("cid"), null, resultSet.getString("title"), null, resultSet.getString("credit")), null, null),
                            resultSet.getInt("grade"),
                            resultSet.getInt("creditsreceived"),
                            CourseRegistrationStatus.valueOf(resultSet.getString("status"))
                    )
            );
        }
        return courseRegisters;
    }

    public List<CourseRegister> getAllSessionRegisteredCourses(Integer studentId) throws SQLException {
        ResultSet resultSet = conn.createStatement().executeQuery("select cr.id as crid, student_id, offer_id, grade, creditsreceived, cr.status as status, c.id as cid, c.title as title, c.credit as credit from (courseregister cr inner join courseoffer co on cr.offer_id = co.id) inner join course c on c.id=co.course_id where cr.student_id = " + studentId + " and cr.status = 'COMPLETED'");
        List<CourseRegister> courseRegisters = new ArrayList<>();
        while (resultSet.next()) {
            courseRegisters.add(
                    new CourseRegister(resultSet.getInt("crid"), new Student(
                            resultSet.getInt("student_id"),
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null
                    ),
                            new CourseOffer(resultSet.getInt("offer_id"), new Course(resultSet.getInt("cid"), null, resultSet.getString("title"), null, resultSet.getString("credit")), null, null),
                            resultSet.getInt("grade"),
                            resultSet.getInt("creditsreceived"),
                            CourseRegistrationStatus.valueOf(resultSet.getString("status"))
                    )
            );
        }
        return courseRegisters;
    }

    public void withdrawFromCourse(Integer registrationId) throws SQLException {
        conn.createStatement().executeUpdate("update courseregister set status = 'DROPPED' where id=" + registrationId);
    }

    public List<CourseOffer> getAllOfferedCourses(Integer studentId) throws SQLException {
        ResultSet resultSet = conn.createStatement().executeQuery("select co.id as id, c.id as cid, c.code as code, c.title as title, c.credit as credit, co.faculty_id as faculty_id from (courseoffer co inner join session s on co.session_id = s.id) inner join course c on co.course_id = c.id where s.status='ACTIVE' and co.id not in (select DISTINCT(co.id) from (courseoffer co inner join session s on co.session_id = s.id) left join courseregister cr on co.id = cr.offer_id where s.status='ACTIVE' and student_id = " + studentId + ")");
        List<CourseOffer> courseOffers = new ArrayList<>();
        while (resultSet.next()) {
            ResultSet resultSet1 = conn.createStatement().executeQuery("select f.id as id, user_id, first_name, last_name from faculty f inner join user u on f.user_id = u.id where f.id=" + resultSet.getInt("faculty_id"));
            resultSet1.next();
            courseOffers.add(
                    new CourseOffer(
                            resultSet.getInt("id"),
                            new Course(resultSet.getInt("cid"), resultSet.getString("code"), resultSet.getString("title"), null, resultSet.getString("credit")),
                            new Faculty(resultSet1.getInt("user_id"), resultSet1.getString("first_name"), resultSet1.getString("last_name"), null, null, null, resultSet1.getInt("id")),
                            null
                    )
            );
        }
        return courseOffers;
    }

    public Integer getAvgCreditForLast2Sems(Integer studentId) throws SQLException {
        ResultSet sessionResultSet = conn.createStatement().executeQuery("select id from session where status<>'ACTIVE' order by year desc, sem desc limit 2");
        List<Integer> credit = new ArrayList<>();
        while (sessionResultSet.next()) {
            ResultSet resultSet = conn.createStatement().executeQuery("select AVG(creditsreceived) as avgcredit from courseregister cr inner join courseoffer co on cr.offer_id = co.id where co.session_id=" + sessionResultSet.getInt("id") + " and student_id=" + studentId + " and cr.status='COMPLETED'");
            resultSet.next();
            credit.add(resultSet.getInt("avgcredit"));
        }
        int sum = 0, num = 0;
        for (Integer c : credit) {
            if (c != 0) {
                sum += c;
                num++;
            }
        }
        if (num == 0)
            return 16;
        return sum / num;
    }

    public void registerStudent(Integer offerId, Integer studentId) throws SQLException {
        conn.createStatement().executeUpdate("insert into courseregister (student_id, offer_id, status) values (" + studentId + ", " + offerId + " ,'" + CourseRegistrationStatus.ENROLLED + "')");
    }

    public MTPInfo getRegisteredMTP(Integer student_id) throws SQLException {
        // Return the MTPInfo of the student if he is already registered to that MTP. Otherwise return null
        ResultSet resultSet = conn.createStatement().executeQuery("select m.id as id, student_id, first_name, last_name, faculty_id, title, domains, credits from (mtpinfo m inner join faculty f on m.faculty_id = f.id) inner join user u on f.user_id = u.id where student_id = " + student_id);
        if (!resultSet.next())
            return null;
        return new MTPInfo(
                resultSet.getInt("id"),
                new Student(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        resultSet.getInt("student_id"),
                        null,
                        null
                ),
                new Faculty(
                        null,
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        null,
                        null,
                        null,
                        resultSet.getInt("faculty_id")
                ),
                resultSet.getString("title"),
                resultSet.getString("domains"),
                resultSet.getInt("credits")
        );
    }

    public List<MTPInfo> getOfferedMTPs() throws SQLException {
        // Return all MTPs which are offered but does not have any students working on it
        List<MTPInfo> mtpinfo = new ArrayList<>();

        ResultSet resultSet = conn.createStatement().executeQuery("select m.id as id, first_name, last_name, faculty_id, title, domains, credits  from (mtpinfo m inner join faculty f on m.faculty_id = f.id) inner join user u on f.user_id = u.id where student_id IS NULL");
        while (resultSet.next()) {
            mtpinfo.add(
                    new MTPInfo(
                            resultSet.getInt("id"),
                            new Student(
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null
                            ),
                            new Faculty(
                                    null,
                                    resultSet.getString("first_name"),
                                    resultSet.getString("last_name"),
                                    null,
                                    null,
                                    null,
                                    resultSet.getInt("faculty_id")
                            ),
                            resultSet.getString("title"),
                            resultSet.getString("domains"),
                            resultSet.getInt("credits")
                    )
            );
        }
        return mtpinfo;
    }

    public void registerForMtp(Integer mtpId, Integer student_id) throws SQLException {
        // Register this student in the MTP with mtpId. Throw SQLException if couldn't register
        ResultSet resultSet = conn.createStatement().executeQuery("select * from mtpinfo where student_id = " + student_id + " AND id = " + mtpId);
        if (!resultSet.isBeforeFirst() && resultSet.getRow() == 0) {
            conn.createStatement().executeUpdate("update mtpinfo set student_id = " + student_id + " where id = " + mtpId);
        }
    }

    public Optional<Student> getStudent(Integer id) throws SQLException {
        ResultSet resultSet = conn.createStatement().executeQuery("select s.id as id, first_name, last_name, user_id, entryno, join_date from (student s inner join user u on s.user_id = u.id) where s.id =" + id);
        if (!resultSet.next())
            return Optional.empty();
        return Optional.of(
                new Student(
                        resultSet.getInt("user_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        null,
                        null,
                        resultSet.getDate("join_date").toLocalDate(),
                        resultSet.getInt("id"), resultSet.getString("entryno"), null
                )
        );
    }


    public List<CourseRegister> getGrade(Integer studentId) throws SQLException {
        List<CourseRegister> courseRegisters = new ArrayList<>();
        ResultSet resultSet = conn.createStatement().executeQuery("select cr.id as id, offer_id, course_id, code, title, description, credit, user_id, first_name, last_name, faculty_id, session_id, sem, year, s.status as session_status, grade, creditsreceived, cr.status as crstatus from " +
                "((((courseregister cr inner join courseoffer co on cr.offer_id = co.id) inner join course c on co.course_id = c.id) inner join faculty f on f.id = co.faculty_id) inner join user u on f.user_id = u.id) inner join session s on s.id = co.session_id " +
                "where student_id = " + studentId);
        while (resultSet.next()) {
            courseRegisters.add(
                    new CourseRegister(
                            resultSet.getInt("id"),
                            null,
                            new CourseOffer(
                                    resultSet.getInt("offer_id"),
                                    new Course(
                                            resultSet.getInt("course_id"),
                                            resultSet.getString("code"),
                                            resultSet.getString("title"),
                                            resultSet.getString("description"),
                                            resultSet.getString("credit")
                                    ),
                                    new Faculty(
                                            resultSet.getInt("user_id"),
                                            resultSet.getString("first_name"),
                                            resultSet.getString("last_name"),
                                            null, null, null,
                                            resultSet.getInt("faculty_id")
                                    ),
                                    new Session(
                                            resultSet.getInt("session_id"),
                                            resultSet.getInt("sem"),
                                            resultSet.getInt("year"),
                                            SessionStatus.valueOf(resultSet.getString("session_status"))
                                            )
                                    ),
                            resultSet.getInt("grade"),
                            resultSet.getInt("creditsreceived"),
                            CourseRegistrationStatus.valueOf(resultSet.getString("crstatus"))
                    )
            );
        }
        return courseRegisters;
    }

    public List<Session> getSession(Integer studentId) throws SQLException {
        ResultSet resultSet = conn.createStatement().executeQuery("select start_session, end_session from (student s inner join batch b on s.batch_id = b.id) where s.id = " + studentId);
        resultSet.next();
        int startSession = resultSet.getInt("start_session");
        int endSession = resultSet.getInt("end_session");
        ResultSet sessionResultSet = conn.createStatement().executeQuery("select * from session where year >= (select year from session where id=" + startSession + ") and year <= (select year from session where id=" + endSession + ") and status = 'COMPLETED' order by year, sem");
        List<Session> sessions = new ArrayList<>();
        while(sessionResultSet.next())
        {
            sessions.add(
                    new Session(
                            sessionResultSet.getInt("id"),
                            sessionResultSet.getInt("sem"),
                            sessionResultSet.getInt("year"),
                            SessionStatus.valueOf(sessionResultSet.getString("status"))
                    )
            );
        }
        return sessions;
    }

    public List<Integer> getFailedCourses(Integer studentId) throws SQLException {
        List<Integer> failedCourses = new ArrayList<>();
        ResultSet resultSet = conn.createStatement().executeQuery("select c.id as course_id from (courseregister cr inner join courseoffer co on cr.offer_id = co.id) inner join course c on co.course_id = c.id where student_id = " + studentId + " and status = 'FAILED'");
        while (resultSet.next())
            failedCourses.add(resultSet.getInt("course_id"));
        return failedCourses;
    }

    public boolean checkPassedCourse(Integer studentId, Integer failedCourseId) throws SQLException {
        ResultSet resultSet = conn.createStatement().executeQuery("select student_id, c.id as course_id from (courseregister cr inner join courseoffer co on cr.offer_id = co.id) inner join course c on co.course_id = c.id where student_id = " + studentId + " and status = 'COMPLETED' and c.id = " + failedCourseId);
        if(!resultSet.next())
            return false;
        return true;
    }

    public List<Integer> getAllCoreCourses(Integer studentId) throws SQLException {
        List<Integer> coreCourses = new ArrayList<>();
        ResultSet resultSet = conn.createStatement().executeQuery("select course_id from batch b inner join offertype ot on ot.batch_id = b.id where b.id in (select batch_id from student s where s.id = " + studentId + ") and type = 'CORE'");
        while(resultSet.next())
            coreCourses.add(resultSet.getInt("course_id"));
        return coreCourses;
    }

    public List<Integer> getAllCompletedCourses(Integer studentId) throws SQLException {
        List<Integer> completedCourses = new ArrayList<>();
        ResultSet resultSet = conn.createStatement().executeQuery("select DISTINCT(course_id) from (courseregister cr inner join courseoffer co on cr.offer_id = co.id) where student_id=" + studentId + " and status='COMPLETED'");
        while(resultSet.next())
            completedCourses.add(resultSet.getInt("course_id"));
        return completedCourses;
    }

    public List<Integer> getAllElectiveCourses(Integer studentId) throws SQLException {
        List<Integer> electiveCourses = new ArrayList<>();
        ResultSet resultSet = conn.createStatement().executeQuery("select DISTINCT(course_id) from (courseregister cr inner join courseoffer co on cr.offer_id = co.id) where course_id not in (select DISTINCT(course_id) from batch b inner join offertype ot on ot.batch_id = b.id where b.id in (select batch_id from student s where s.id = "+studentId+") and type = 'CORE') and student_id="+studentId+" and status = 'COMPLETED'");
        while (resultSet.next())
            electiveCourses.add(resultSet.getInt("course_id"));
        return electiveCourses;
    }

    public Optional<Integer> getMtp(Integer studentId) throws SQLException {
        ResultSet resultSet = conn.createStatement().executeQuery("select credits from mtpinfo where student_id = " + studentId);
        if(!resultSet.next())
            return Optional.empty();
        return Optional.of(resultSet.getInt("credits"));

    }
}