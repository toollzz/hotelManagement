package edu.bowiestate.hotelManagement.reservation;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/*import java.time.LocalDate;
import java.time.LocalDate;
import java.util.Date;*/

public class ReservationDetailForm {
    @Length(min = 1, max=50)
    private String firstname;
    private char middle;
    @Length(min = 1, max=50)
    private String lastname;
    @Length(min = 1, max=50)
    private String telephone;
    @Length(min = 1, max=50)
    private String Email;
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    private char doNotDisturb;
    public char getDoNotDisturb() {
        return doNotDisturb;
    }

    public void setDoNotDisturb(char doNotDisturb) {
        this.doNotDisturb = doNotDisturb;
    }



    @Length(min = 1, max=50)
    private String Address;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
   // @Length(min = 1, max=10)
    private Long CONFIRMNUM;
    public Long getCONFIRMNUM() {
        return CONFIRMNUM;
    }

    public Long setCONFIRMNUM(Long CONFIRMNUM) {this.CONFIRMNUM = CONFIRMNUM;
        return CONFIRMNUM;
    }
    private Long CUSTOMER_ID;
    public Long getCUSTOMER_ID() {
        return CUSTOMER_ID;
    }

    public void setCUSTOMER_ID(Long CUSTOMER_ID) {
        this.CUSTOMER_ID = CUSTOMER_ID;
    }
    private long  ROOM_NUM;
    public long getROOM_NUM() {
        return ROOM_NUM;
    }

    public void setROOM_NUM(long ROOM_NUM) {
        this.ROOM_NUM = ROOM_NUM;
    }
    private String Status;
    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
    private double price ;
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

  @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate STARTDATES ;

    public LocalDate getSTARTDATES() {
        return STARTDATES;
    }

    public void setSTARTDATES(LocalDate  STARTDATES) {
        this.STARTDATES = STARTDATES;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate  ENDDATES ;
    public LocalDate getENDDATES() {
        return ENDDATES;
    }

    public void setENDDATES(LocalDate  ENDDATES) {
        this.ENDDATES = ENDDATES;
    }
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


}
