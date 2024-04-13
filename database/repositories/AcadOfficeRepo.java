package database.repositories;

import database.models.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AcadOfficeRepo extends Repository {

    public List<Course> getCourses() throws SQLException {
        //Fetch all the courses and return them
        List<Course> courses = new ArrayList<>();
        ResultSet result = conn.createStatement().executeQuery("SELECT * FROM course");
        while (result.next()) {
            courses.add(
                    new Course(
                            result.getInt("id"),
                            result.getString("code"),
                            result.getString("title"),
                            result.getString("description"),
                            result.getString("credit"),
                            getPrerequisites(result.getInt("id"))
                    )
            );
        }
        return courses;
    }

    public List<Course> getPrerequisites(Integer courseId) throws SQLException {
        List<Course> prereq = new ArrayList<>();
        ResultSet result2 = conn.createStatement().executeQuery("SELECT c2.id AS id, c2.code AS code, c2.title AS title, c2.description AS description, c2.credit AS credit FROM course c1 INNER JOIN prerequisite p ON c1.id = p.course_id INNER JOIN course c2 ON c2.id = p.prereq_course_id WHERE c1.id=" + courseId);
        while (result2.next()) {
            prereq.add(
                    new Course(
                            result2.getInt("id"),
                            result2.getString("code"),
                            result2.getString("title"),
                            result2.getString("description"),
                            result2.getString("credit")
                    )
            );
        }
        return prereq;
    }

    public Optional<Course> getCourse(String code) throws SQLException {
        //fetch the course
        ResultSet result = conn.createStatement().executeQuery("SELECT * FROM course where code = '" + code + "'");
        if (!result.next())
            return Optional.empty();

        List<Course> prereq = new ArrayList<>();
        ResultSet result2 = conn.createStatement().executeQuery("SELECT c2.id AS id, c2.code AS code, c2.title AS title, c2.description AS description, c2.credit AS credit" +
                " FROM course c1 INNER JOIN prerequisite p ON c1.id = p.course_id INNER JOIN course c2 ON c2.id = p.prereq_course_id" +
                " WHERE c1.id=" + result.getInt("id"));
        while (result2.next()) {
            prereq.add(
                    new Course(
                            result2.getInt("id"),
                            result2.getString("code"),
                            result2.getString("title"),
                            result2.getString("description"),
                            result2.getString("credit")
                    )
            );
        }

        return Optional.of(
                new Course(
                        result.getInt("id"),
                        result.getString("code"),
                        result.getString("title"),
                        result.getString("description"),
                        result.getString("credit"),
                        prereq
                )
        );
    }

    public void addPrerequisites(Course course) throws SQLException {
        List<Course> prereq_course = course.getPrerequisites();
        //inserting the prerequisite courses id for given courses in prerequisite table
        if (!prereq_course.isEmpty())
            for (Course a : prereq_course)
                conn.createStatement().executeUpdate("insert into prerequisite(course_id, prereq_course_id) values (" + course.getId() + ", (select id from course where code='" + a.getCode() + "'))");

    }

    public void deletePrerequisites(Integer courseId) throws SQLException {
        conn.createStatement().executeUpdate("delete from prerequisite where course_id=" + courseId);
    }

    public void addCourse(Course course) throws SQLException { //prerequisite course code needed
        //add a course
        conn.createStatement().executeUpdate("insert into course(code, title, description, credit) values ('" + course.getCode() + "','" + course.getTitle() + "','" + course.getDescription() + "','" + course.getCredit() + "')");
        ResultSet resultSet = conn.createStatement().executeQuery("select id from course where code='" + course.getCode() + "'");
        if (resultSet.next())
            course.setId(resultSet.getInt("id"));
        addPrerequisites(course);
    }

    public void editCourseDetails(Course course) throws SQLException {
        //edit a course
        conn.createStatement().executeUpdate("update course " +
                "set code = '" + course.getCode() + "', title = '" + course.getTitle() + "', description = '" + course.getDescription() + "', credit = '" + course.getCredit() + "' " +
                "where id=" + course.getId());
    }

    public void deleteCourse(String code) throws SQLException {
        //delete a course
        conn.createStatement().executeUpdate("delete from course where code = '" + code + "'");
    }

    public Optional<Student> getStudent(String entry) throws SQLException {
        ResultSet resultSet = conn.createStatement().executeQuery("select s.id as id, first_name, last_name, user_id, entryno, join_date from (student s inner join user u on s.user_id = u.id) where entryno ='" + entry+"'");
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

}