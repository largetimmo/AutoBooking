package dao;

import pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDAO {

    private ConnectionPool connectionPool;

    public UserDAO() {

    }

    public void setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;

    }

    /**
     * add user to database
     * @param user
     * @return
     */
    public boolean addUser(User user) {
        String sql = "INSERT INTO User(username,password) VALUES(?,?)";
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.execute();
            preparedStatement.close();
            connectionPool.putConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * get user by giving id
     * @param id
     * @return
     */
    public User getUserByID(int id) {
        User user = new User();
        String sql = "SELECT * FROM User WHERE uid = ?";
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                int uid = resultSet.getInt(resultSet.findColumn("uid"));
                String username = resultSet.getString(resultSet.findColumn("username"));
                String password = resultSet.getString(resultSet.findColumn("password"));
                user.setPassword(password);
                user.setUid(uid);
                user.setUsername(username);
            }
            preparedStatement.close();
            connectionPool.putConnection(connection);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;

    }

    /**
     * get user who is not booking any room by giving date
     * @param date
     * @return
     */
    public List<User> getUserNotBookingByDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date_str = simpleDateFormat.format(date);
        Connection connection = connectionPool.getConnection();
        List<User> user_list = new ArrayList<>();
        String sql = "SELECT uid FROM User WHERE NOT exists(SELECT * FROM Appointment WHERE Date LIKE ? AND User.uid = Appointment.Uid )";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,date_str+"%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                user_list.add(getUserByID(resultSet.getInt(1)));
            }
            preparedStatement.close();
            connectionPool.putConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user_list;
    }


}
