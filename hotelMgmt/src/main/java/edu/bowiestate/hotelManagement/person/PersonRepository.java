package edu.bowiestate.hotelManagement.person;

import edu.bowiestate.hotelManagement.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person,Long> {

    Person findByFirstnameAndLastname(String firstname, String lastname);

    @Query(nativeQuery = true, value = "Select * from Person where ID = :ID")
    Person findByPersonID(@Param("ID") int ID );


}
