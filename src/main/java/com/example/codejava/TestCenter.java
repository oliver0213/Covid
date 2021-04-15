package com.example.codejava;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "test_center")
public class TestCenter
{
    @Id
    private long centerId;

    private String name;

    private String address;

    private String zipCode;

    public long getCenterId()
    {
        return centerId;
    }

    public void setCenterId(long centerId)
    {
        this.centerId = centerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }


}