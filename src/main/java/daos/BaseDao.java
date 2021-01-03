package daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseDao {
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String user = "root";
        String password = "root";
        String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=America/Lima";

        return DriverManager.getConnection(url, user, password);
    }
}
