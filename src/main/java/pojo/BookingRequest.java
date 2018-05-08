package pojo;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BookingRequest {
    /*
        # Argument format:
    # 1 -> username
    # 2 -> password
    # 3-> datetime(int)
    # 4 -> starttime(int)
    # 5 -> duration(int)
    # 6 -> roomnumber
     */
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    private static final long SEVEN_DAYS_SEC = 604800;
    private String username;
    private String password;
    private String room;
    private int startHour;
    private int startMinute;
    private int durationHour;
    private int durationMinute;

    private static String parseTime(int hour,int minute){
        minute+=(hour*60);
        return Integer.toString(minute);
    }
    public static String getDateTime(){
        Date today = Calendar.getInstance().getTime();
        String today_str = SDF.format(today);
        try {
            today = SDF.parse(today_str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long daydiff = today.getTime()/1000;
        daydiff+=SEVEN_DAYS_SEC;
        return Long.toString(daydiff);
    }




    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getDurationHour() {
        return durationHour;
    }

    public void setDurationHour(int durationHour) {
        this.durationHour = durationHour;
    }

    public int getDurationMinute() {
        return durationMinute;
    }

    public void setDurationMinute(int durationMinute) {
        this.durationMinute = durationMinute;
    }

    public String[] constructArr(){
        String[] arg_Arr = new String[6];
        arg_Arr[0] = username;
        arg_Arr[1] = password;
        arg_Arr[2] = getDateTime();
        arg_Arr[3] = parseTime(startHour,startMinute);
        arg_Arr[4] = parseTime(durationHour,durationMinute);
        arg_Arr[5] = room;
        return arg_Arr;
    }
    public static class Builder{
        private final BookingRequest instance = new BookingRequest();
        public BookingRequest create(){
            return instance;
        }
        public Builder setUsername(String username){
            instance.username = username;
            return this;
        }
        public Builder setPassword(String password){
            instance.password = password;
            return this;
        }
        public Builder setRoom(String room){
            instance.room = room;
            return this;
        }
        public Builder setStartHour(int hour){
            instance.startHour = hour;
            return this;
        }
        public Builder setStartMinute(int minute){
            instance.startMinute = minute;
            return this;
        }
        public Builder setDurationHour(int hour){
            instance.durationHour = hour;
            return this;
        }
        public Builder setDurationMinute(int minute){
            instance.durationMinute = minute;
            return this;
        }
    }

}
