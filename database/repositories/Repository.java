package database.repositories;

import database.DBCredentials;
import database.models.Course;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Repository {

    Connection conn;

    public Repository() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DBCredentials.dbUrl, DBCredentials.username, DBCredentials.password);
        } catch (Exception e) {
            throw new DBConnectionException("Could not connect to database!");
        }
    }


}