package database.repositories;

import database.models.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public class AuthRepo extends Repository {

    public Optional<Auth> getAuth(String email) throws SQLException {
        ResultSet resultSet = conn.createStatement().executeQuery("select * from " +
                "auth inner join user on auth.user_id = user.id " +
                "where email = '" +
                email +
                "'");
        if (!resultSet.next())
            return Optional.empty();
        User user = new User(
                resultSet.getInt("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("email"),
                resultSet.getString("phone"),
                resultSet.getDate("join_date").toLocalDate()
        );
        Auth auth = new Auth(user, resultSet.getString("password"));
        return Optional.of(auth);
    }

    public Optional<Integer> getStudent(Integer userId) throws SQLException {
        ResultSet resultSet = conn.createStatement().executeQuery("select s.id as id from user u inner join student s on u.id = s.user_id where u.id = " + userId);
        if(!resultSet.next())
            return Optional.empty();
        return Optional.of(resultSet.getInt("id"));
    }

    public Optional<Integer> getFaculty(Integer userId) throws SQLException {
        ResultSet resultSet = conn.createStatement().executeQuery("select f.id as id  from user u inner join faculty f on u.id = f.user_id where u.id = " + userId);
        if(!resultSet.next())
            return Optional.empty();
        return Optional.of(resultSet.getInt("id"));
    }

    public Optional<Integer> getAcademicOffice(Integer userId) throws SQLException {
        ResultSet resultSet = conn.createStatement().executeQuery("select a.id as id from user u inner join academicoffice a on u.id = a.user_id where u.id = " + userId);
        if(!resultSet.next())
            return Optional.empty();
        return Optional.of(resultSet.getInt("id"));
    }


    public void addLoginSession(Auth auth) throws SQLException {
        conn.createStatement().executeUpdate("insert into loginsession(user_id, timestamp) values("+auth.getUser().getId()+", '"+ LocalDateTime.now() +"')");
    }

    public void deleteLoginSession(Integer userId) throws SQLException {
        conn.createStatement().executeUpdate("delete from loginsession where user_id = "+userId);
    }

    public Optional<LoginSession> getLoginSession(Integer userId) throws SQLException {
        ResultSet resultSet = conn.createStatement().executeQuery("select * from loginsession l inner join user u on l.user_id = u.id where l.user_id = '" + userId + "' order by l.id desc limit 1");
        if(!resultSet.next())
            return Optional.empty();
        User user = new User(
                resultSet.getInt("user_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("email"),
                resultSet.getString("phone"),
                resultSet.getDate("join_date").toLocalDate()
                );
        return Optional.of(new LoginSession(resultSet.getInt("id"), user, resultSet.getTimestamp("timestamp").toLocalDateTime()));
    }
}