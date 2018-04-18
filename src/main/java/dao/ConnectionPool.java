package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Connection pool
 * Maintain all connections with mysql server
 */
public class ConnectionPool {
    private static final int INITIAL_CONNECTION = 10;
    private static final String Url = "jdbc:mysql://127.0.0.1:3306/AutoBooking";
    private List<Connection> connectionQueue;

    public ConnectionPool() throws ClassNotFoundException, SQLException {
        connectionQueue = Collections.synchronizedList(new LinkedList<>());
        Class.forName("com.mysql.jdbc.Driver");
        initial();
    }

    private void initial() throws SQLException {
        for (int i = 0; i < INITIAL_CONNECTION; i++) {
            Connection connection = DriverManager.getConnection(Url, "root", "221600");
            connectionQueue.add(connection);
        }
    }

    public Connection getConnection() {
        if (connectionQueue.size() == 0) {
            return null;
        }
        return connectionQueue.get(0);
    }

    public void putConnection(Connection connection) {
        connectionQueue.add(connection);
    }
}
