package util;

import dao.AppointmentDAO;
import dao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import pojo.Appointment;
import pojo.BookingRequest;
import pojo.User;
import java.io.IOException;
import java.util.Date;
import java.util.List;
@DisallowConcurrentExecution
public class BookingJob{
    private static Logger logger = LogManager.getLogger(ScriptExecutor.class);//initial logger
    private static final String ROOM = "324D";//booking for only one room for now
    @Autowired
    private AppointmentDAO appointmentDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ScriptExecutor executor;

    //todo
    public BookingJob(){
        logger.debug("BookingJob Loaded");
    }

    public void setAppointmentDAO(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setExecutor(ScriptExecutor executor) {
        this.executor = executor;
    }

    public void doIt(){
        try {
            //try to avoid the job runs too fast and cause it running multiple times in one sec
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.debug("Schedule Task started");
        logger.debug("Time is "+new Date());

        Resource script = new ClassPathResource("/script/autobooking.py");
        String scriptpath = null;

        try {
            scriptpath = script.getFile().getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(scriptpath == null){
            //ERROR, abort
            //logger.error("Cannot find python script.Abort!!");
            return;
        }
        List<User> allUsers = userDAO.getUserNotBookingByDate(new Date());
        int startHour = 10;//10am
        int duration = 2; // 2 hour, MAX appointment duration

        while(allUsers.size() > 6){
            //more than 6 users -> book another room or extend appointment duration TBD
            //for now just assume maximum use 6 accounts to book room from 10am to 10pm, 12hours total
            //todo
            allUsers.remove(allUsers.size()-1);//remove last one

        }

        for (User user : allUsers){
            logger.debug("Current user+"+user);
            BookingRequest bookingRequest = new BookingRequest.Builder()
                    .setDurationHour(duration)
                    .setDurationMinute(0)
                    .setStartHour(startHour)
                    .setStartMinute(0)
                    .setPassword(user.getPassword())
                    .setRoom(ROOM)
                    .setUsername(user.getUsername())
                    .create();
            String[] parameterArray = bookingRequest.constructArr();
            String result = executor.execute(scriptpath,parameterArray);

            if(result.startsWith("ML")){
                Appointment appointment = new Appointment();
                appointment.setBookID(result);
                appointment.setDuration(duration);
                appointment.setStartTime(startHour);
                appointment.setDate(new Date());
                appointment.setUser(user);
                appointment.setRoom(ROOM);
                appointmentDAO.add(appointment);
                startHour+=duration;
            }
        }

        System.out.println("Done");
    }
}
