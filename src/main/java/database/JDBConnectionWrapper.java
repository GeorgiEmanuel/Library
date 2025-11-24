package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static com.mysql.cj.conf.PropertyKey.allowMultiQueries;
import static java.lang.Class.forName;

public class JDBConnectionWrapper {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    private static final String DB_URL = "jdbc:mysql://localhost/";

    private static final String USER = "root";

    private static final String PASSWORD = "root1234PT";

    private static final int TIMEOUT = 5;

    private Connection connection;

    public JDBConnectionWrapper(String schema) {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL + schema + "?allowMultiQueries=true", USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean testConnection() throws SQLException {
        return connection.isValid(TIMEOUT);
    }

    public Connection getConnection() {
        return this.connection;
    }
}
