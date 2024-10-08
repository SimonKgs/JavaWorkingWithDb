package gym.dbconection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
    // this file will have the connections to my db
    public static Connection getConnection(){

        Connection connection = null;
        String dbName = "gym";
        // jdbc is a common api included in Java to make the connections and operation with the db
        // then the type of db implemented this time is mysql but it could be other like postgresql
        String url  = "jdbc:mysql://localhost:3306/" + dbName;
        // now authentication which we defined on the creation of the db
        String user = "root";
        String password = "sIM21as!!a542312osASKma//"; // Later I must learn how to use a .env on Java
        // making the authentication
        try {
            // driver class
            // this will load a class on runtime
            // this is a common practice in older version, LOOK for the new way:
            // com.mysql: The package name where the driver is located.
            // cj: Refers to the "Connector/J" (the MySQL JDBC driver).
            // jdbc.Driver: The actual driver class that implements the java.sql.Driver interface,
            // allowing Java applications to connect to a MySQL database.
            // The next line seems unnecessary now, from jdbc 4+ this happens automatically on the connection
            // Class.forName("com.mysql.cj.jdbc.Driver"); // tested now this line is not needed
            connection = DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            System.out.println("Error connecting to db: " + e.getMessage());
        }

        return connection;
    }

    // public static void main(String[] args) {
    //    // here I will test the connection
    //    Connection connection = DB.getConnection();
    //    if (connection != null)
    //        System.out.println("Connection success: " + connection);
    //    else
    //        System.out.println("There was an error on the connection");
    //}
}
