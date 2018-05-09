package dao;


import org.springframework.beans.factory.annotation.Autowired;
import pojo.Appointment;
import pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentDAO {
    @Autowired
    private ConnectionPool connectionPool;
    @Autowired
    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void add(Appointment appointment) {
        String sql = "INSERT INTO Appointment(bookingid,uid,`date`,starttime,duration,room) VALUES(?,?,?,?,?,?)";
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, appointment.getBookID());
            preparedStatement.setInt(2, appointment.getUser().getUid());
            preparedStatement.setString(3, sdf.format(appointment.getDate()));
            preparedStatement.setInt(4, appointment.getStartTime());
            preparedStatement.setInt(5, appointment.getDuration());
            preparedStatement.setString(6, appointment.getRoom());
            preparedStatement.execute();
            preparedStatement.close();
            connectionPool.putConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Appointment> list() {
        List<Appointment> appointmentList = new ArrayList<>();
        String sqlquery = "SELECT * FROM Appointment";
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlquery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                appointmentList.add(injectValue(resultSet));
            }
            preparedStatement.close();
            connectionPool.putConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentList;
    }

    public Appointment getAppointmentByID(String bookingID) {
        Appointment appointment = null;
        String sqlquery = "SELECT * FROM Appointment WHERE BookingID = ?";
        Connection connection = connectionPool.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlquery);
            preparedStatement.setString(1, bookingID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                appointment = injectValue(resultSet);
            }
            preparedStatement.close();
            connectionPool.putConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointment;
    }

    /**
     * Parse resultset to object
     * @param resultSet
     * @return
     */
    private Appointment injectValue(ResultSet resultSet) {
        Appointment appointment = new Appointment();
        try {
            User user = userDAO.getUserByID(resultSet.getInt(resultSet.findColumn("Uid")));
            Date date = sdf.parse(resultSet.getString(resultSet.findColumn("Date")));
            int starttime = resultSet.getInt(resultSet.findColumn("Starttime"));
            int duration = resultSet.getInt(resultSet.findColumn("Duration"));
            String room = resultSet.getString(resultSet.findColumn("Room"));
            appointment.setRoom(room);
            appointment.setUser(user);
            appointment.setDuration( duration);
            appointment.setStartTime(starttime);
            appointment.setDate(date);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return appointment;
    }


}
