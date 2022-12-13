package edu.bowiestate.hotelManagement.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(nativeQuery = true, value = "Select * from Reservation where :currentDay between start_date and end_date" +
            " and status in ('OPEN','IN_PROGRESS') order by start_date ")
    public List<Reservation> findByTodaysReservations(@Param("currentDay") LocalDate currentDay);

    public List<Reservation> findByStartDateGreaterThanAndStatusOrderByStartDate(LocalDate date, Reservation.ReservationStatus status);

    @Query(nativeQuery = true, value = "Select * from Reservation ")
    public List<Reservation> FindAllReservation();

    @Query(nativeQuery = true, value = "Select * from Reservation ")
    public List<Reservation> FindAllReserv();

    @Query(nativeQuery = true, value = "Select * from Reservation where room_num = :room_num   ")
    public Reservation FindReservationByRoomNum(@Param("room_num") Long room_num);

    @Query(nativeQuery = true, value = "Select * from Reservation where CONFIRM_NUM   = :CONFIRM_NUM     ")
    public Reservation FindReservationByConfirmNum(@Param("CONFIRM_NUM") Long CONFIRM_NUM);

  /*  @Query(nativeQuery = true, value = "Select * from Reservation r inner join Customer c on r.Customer_id = c.cust_id inner join Person p on r.Customer_id = p.id where r.CONFIRM_NUM   = :CONFIRM_NUM      ")
    public List<Object[]> FindReservationByConfirmNums(@Param("CONFIRM_NUM") Long CONFIRM_NUM);
*/


}

