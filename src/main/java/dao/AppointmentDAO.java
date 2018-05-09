package dao;


import org.springframework.beans.factory.annotation.Autowired;
import pojo.Appointment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class AppointmentDAO {
    @Autowired
    private ConnectionPool connectionPool;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public void setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }
    public void add(Appointment appointment){
        String sql = "INSERT INTO Appointment(bookingid,uid,`date`,starttime,duration,room) VALUES(?,?,?,?,?,?)";
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,appointment.getBookID());
            preparedStatement.setInt(2,appointment.getUser().getUid());
            preparedStatement.setString(3,sdf.format(appointment.getDate()));
            preparedStatement.setInt(4,appointment.getStartTime());
            preparedStatement.setInt(5, appointment.getDuration());
            preparedStatement.setString(6, appointment.getRoom());
            preparedStatement.execute();
            preparedStatement.close();
            connectionPool.putConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Appointment> list(){
        //todo
        return null;
    }


}
