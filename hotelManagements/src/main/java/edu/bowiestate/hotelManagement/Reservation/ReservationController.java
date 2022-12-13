package edu.bowiestate.hotelManagement.Reservation;

import edu.bowiestate.hotelManagement.employee.Employee;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class ReservationController {


    @GetMapping(value= "/CheckReservation")
    public String CheckReservation() {
        return "CheckReservation";
    }

    @GetMapping(value= "/MakeReservation")
    public String MakeReservation() {
        return "MakeReservation";
    }

}
