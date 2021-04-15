package com.example.codejava;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.persistence.*;


@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Patient_id")
    private String patientId;

    @Column(name = "Date")
    private String date;

    @Column(name = "Timeslot")
    private String timeslot;

    @Column(name = "Test_center")
    private String testCenter;

    @Column(name = "Test_Type")
    private String testType;

    @Column(name = "Type")
    private String type;

    private String status;

    @Lob
    private byte[] image;


    @ManyToOne()
    @JoinColumn(name = "Patient_id",referencedColumnName="email",updatable =
            false,insertable = false)
    private PatientUser user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String  getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    public String getTestCenter() {
        return testCenter;
    }

    public void setTestCenter(String testCenter) {
        this.testCenter = testCenter;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImage64() {
        if (null != image) {
            return Base64.encodeBase64String(image);
        }
        return null;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public PatientUser getUser() {
        return user;
    }

    public void setUser(PatientUser user) {
        this.user = user;
    }

    public boolean isUploaded(){
        return image != null;
    }
}
