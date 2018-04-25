package pojo;



public class BookingRequest {
    private String username;
    private String password;
    private String room;
    private int startHour;
    private int startMinute;
    private int durationHour;
    private int durationMinute;

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
