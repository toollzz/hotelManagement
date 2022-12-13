package edu.bowiestate.hotelManagement.person;

import org.hibernate.validator.constraints.Length;

public class PersonInput {
    @Length(min = 1, max=50)
    private String firstname;
    private char middle;
    @Length(min = 2, max=50)
    private String lastname;
    @Length(min = 12, max=12)
    private String telephone;

    @Length(min = 10, max=50)
    private String address;

    @Length(min = 4, max=50)
    private String city;
    @Length(min = 2, max=2)
    private String state;
    @Length(min = 5, max=5)
    private String zipcode;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public char getMiddle() {
        return middle;
    }

    public void setMiddle(char middle) {
        this.middle = middle;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
