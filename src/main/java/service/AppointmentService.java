package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pojo.Appointment;

import java.util.List;

public interface AppointmentService {
    Appointment getAppointment(int id);
    List<Appointment> getAllAppointment();
}
