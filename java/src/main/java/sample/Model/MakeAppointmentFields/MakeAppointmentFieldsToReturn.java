package sample.Model.MakeAppointmentFields;

import java.sql.Time;

public class MakeAppointmentFieldsToReturn {
    private String doctorsName;
    private Time time;

    public String getDoctorsName() {
        return doctorsName;
    }

    public MakeAppointmentFieldsToReturn(String doctorsName, Time time) {
        this.doctorsName = doctorsName;
        this.time = time;
    }

    public void setDoctorsName(String doctorsName) {
        this.doctorsName = doctorsName;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
