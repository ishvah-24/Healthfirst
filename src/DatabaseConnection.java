import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL =
            "jdbc:mysql://localhost:3306/healthfirst_db";

    private static final String USER = "root";
    private static final String PASSWORD = "2404";

    public static Connection getConnection(){
        try{
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to MySQL!");
            return connection;

        }catch(SQLException e){
            System.out.println("Connection failed!");
            e.printStackTrace();

            return null;
        }
    }
}
