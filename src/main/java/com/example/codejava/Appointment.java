package com.example.codejava;

import javax.persistence.*;


@Entity
@Table(name = "appointment")

public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "`Test Center`")
    private String Test_Center ;

    @Column(name = "`Date`")
    private String Date ;

    @Column(name = "`Time`")
    private String Time;

    @Column(name = "`Number of Slots`")
    private Long Number_of_Slots;

    @Column(name = "`Type`")
    private String Type;


    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getTest_Center() {
        return Test_Center;
    }

    public void setTest_Center(String test_Center) {
        Test_Center = test_Center;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public Long getNumber_of_Slots() {
        return Number_of_Slots;
    }

    public void setNumber_of_Slots(Long number_of_Slots) {
        Number_of_Slots = number_of_Slots;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }



}