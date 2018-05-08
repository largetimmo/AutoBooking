package util;

import dao.AppointmentDAO;
import dao.UserDAO;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionException;
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

public class BookingJob{
    private static Logger logger = Logger.getLogger(ScriptExecutor.class);//initial logger
    private static final String ROOM = "324D";//booking for only one room for now
    //todo
    public BookingJob(){
        logger.debug("BookingJob Loaded");
    }
    public void doIt(){
        System.out.println("123\n\n\n\n\n\n");
        BasicConfigurator.configure();
        logger.setLevel(Level.DEBUG);
        logger.debug("Schedule Task started");
        logger.debug("Time is "+new Date());
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        AppointmentDAO appointmentDAO = applicationContext.getBean(AppointmentDAO.class);
        UserDAO userDAO =  applicationContext.getBean(UserDAO.class);
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
        ScriptExecutor executor = applicationContext.getBean(ScriptExecutor.class);
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
            }
        }
        System.out.println("Done");
    }
}
