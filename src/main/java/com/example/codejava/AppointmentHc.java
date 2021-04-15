package com.example.codejava;


import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


@Entity
@Table(name = "appointment")
public class AppointmentHc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long appointmentId;


    @Column(name = "`Number of Slots`")
    private Long invitedCount;

    @Column(name = "`Test Center`")
    private String testCenter;


    @Transient
    private Integer month;

    @Transient
    private Integer day;

    @Transient
    private Integer year;

    private String time;


    @Column(name = "type")
    private String type;


    @Column(name = "Date")
    private String date;

    @Transient
    private Long centerId;

    public Integer getMonth() {
        if (null != date) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(parseDate());
            return gc.get(Calendar.MONTH) + 1;
        }
        return null;
    }

    private Date parseDate() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Integer getYear() {
        if (null != date) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(parseDate());
            return gc.get(Calendar.YEAR);
        }
        return null;
    }

    public Integer getDay() {
        if (null != date) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(parseDate());
            return gc.get(Calendar.DAY_OF_MONTH);

        }
        return null;
    }

    public void buildRecord() {
        if (null != year) {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                this.date = sdf.format(sdf.parse(String.format("%s-%s-%s", year,
                        month, day)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getInvitedCount() {
        return invitedCount;
    }

    public void setInvitedCount(Long invitedCount) {
        this.invitedCount = invitedCount;
    }

    public String getTestCenter() {
        return testCenter;
    }

    public void setTestCenter(String testCenter) {
        this.testCenter = testCenter;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getCenterId() {
        return centerId;
    }

    public void setCenterId(Long centerId) {
        this.centerId = centerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
