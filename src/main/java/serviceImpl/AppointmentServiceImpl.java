package serviceImpl;

import dao.AppointmentDAO;
import dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pojo.Appointment;
import service.AppointmentService;

import java.util.List;

@Component
public class AppointmentServiceImpl implements AppointmentService {
    private UserDAO userDAO;
    private AppointmentDAO appointmentDAO;

    public AppointmentServiceImpl(UserDAO userDAO, AppointmentDAO appointmentDAO) {
        this.userDAO = userDAO;
        this.appointmentDAO = appointmentDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setAppointmentDAO(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }




    @Override
    public Appointment getAppointment(String bookingID) {
        return appointmentDAO.getAppointmentByID(bookingID);
    }

    @Override
    public List<Appointment> getAllAppointment() {
        return appointmentDAO.list();
    }
}
