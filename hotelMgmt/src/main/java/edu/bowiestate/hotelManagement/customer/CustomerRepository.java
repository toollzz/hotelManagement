package edu.bowiestate.hotelManagement.customer;

import edu.bowiestate.hotelManagement.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(nativeQuery = true, value = "Select * from Customer where CUST_ID = :CUST_ID ")
    public Customer FindCustomerById(@Param("CUST_ID") Long CUST_ID);
}
