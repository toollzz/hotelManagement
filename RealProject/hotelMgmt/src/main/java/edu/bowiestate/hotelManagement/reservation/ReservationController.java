package edu.bowiestate.hotelManagement.reservation;

import edu.bowiestate.hotelManagement.StripePayment.ChargeRequest;
import edu.bowiestate.hotelManagement.employee.EmployeeCredentialsForm;
import edu.bowiestate.hotelManagement.person.Person;
import edu.bowiestate.hotelManagement.person.PersonInput;
import edu.bowiestate.hotelManagement.person.PersonService;
import edu.bowiestate.hotelManagement.room.Room;
import edu.bowiestate.hotelManagement.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Controller
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private PersonService personService;

    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_RECEPT')")
    @GetMapping("/reservation/current")
    public String getCurrentDaysReservations(Model model) {
        model.addAttribute("reservations",reservationService.findCurrentDaysReservations());
        return "currentReservations";
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_RECEPT')")
    @GetMapping("/reservation/upcoming")
    public String getUpcomingReservations(Model model) {
        model.addAttribute("reservations",reservationService.findFutureReservations());
        return "upcomingReservations";
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_RECEPT')")
    @GetMapping("/reservation/{id}/checkIn")
    public String checkIn(@PathVariable long id, HttpServletRequest request){
        // need to direct to payment page before we get here
        reservationService.checkIn(id);
        String targetUrl = request.getHeader("referer");
        return "redirect:" + targetUrl;
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_RECEPT')")
    @GetMapping("/reservation/{id}/checkout")
    public String checkOut(@PathVariable long id, HttpServletRequest request){
        reservationService.checkout(id);
        String targetUrl = request.getHeader("referer");
        return "redirect:" + targetUrl;
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_RECEPT')")
    @GetMapping("/reservation/{id}/cancel")
    public String cancel(@PathVariable long id,HttpServletRequest request){
        reservationService.cancel(id);
        String targetUrl = request.getHeader("referer");
        return "redirect:" + targetUrl;
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_RECEPT')")
    @GetMapping("/reservation/{id}/update")
    public String getUpdatePage(@PathVariable long id, Model model){
        model.addAttribute("reservation",reservationService.findByConfirmationNum(id));
        model.addAttribute("reservationUpdateForm", new ReservationUpdateForm());
        model.addAttribute("availableRooms", roomService.getAllAvailableRooms());
        return "reservationUpdate";
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_RECEPT')")
    @PostMapping("/reservation/update")
    public String updateReservation(@Valid ReservationUpdateForm reservationUpdateForm, BindingResult bindingResult, Model model){
        reservationService.updateReservation(reservationUpdateForm);
        model.addAttribute("reservation",reservationService.findByConfirmationNum(reservationUpdateForm.getConfirmNum()));
        model.addAttribute("reservationUpdateForm", reservationUpdateForm);
        model.addAttribute("availableRooms", roomService.getAllAvailableRooms());
        model.addAttribute("updateSuccessful", true);
        return "redirect:/reservation/"+ reservationUpdateForm.getConfirmNum() + "/update";
    }

    @GetMapping(value= "/CheckReservation")
    public String GetCheckReservation(Model model) {

        model.addAttribute("ReservationDetailForms", new ReservationDetailForm());

        return "CheckReservation";
    }

    @GetMapping(value= "/MakeReservation")
    public String MakeReservation(Model model, ReservationRoomDto rdt) {

        model.addAttribute("ReservationDetailForm", new ReservationDetailForm());
        model.addAttribute("rooms",roomService.getAllRooms());
        List<Room> room =  roomService.findAllRooms();

        return  "MakeReservation";
    }


    @PostMapping("/newReservation/reservev")
    public String saveNewReservation(@Valid ReservationDetailForm _rdf, Model model) throws ParseException {

           System.out.println("Enter");
           Person person = new Person();
           Person p = new Person();
           Reservation r = new Reservation();
           Room room = new Room();
           Long yy = GenCustomerId();
           String str = Long.toString(yy);
           System.out.println("mkl"+yy);
        //System.out.println(_rdf.setCUSTOMER_ID(89765));
           System.out.println(_rdf.getENDDATES());
           System.out.println("conNum" + _rdf.setCONFIRMNUM(yy));
           Long DiffD = DiffInDays(_rdf.getSTARTDATES(),_rdf.getENDDATES());
        System.out.println("DiffD" + DiffD);
        //p.setId((long) yy);
        System.out.println(_rdf.getFirstname());
        System.out.println(_rdf.getLastname());
        System.out.println(_rdf.getROOM_NUM());
        r = reservationService.saveReservation(_rdf);
        System.out.println("conNum1" + _rdf.setCONFIRMNUM(yy));

          if(_rdf != null)
          {
              return "redirect:/Payment/" + r.getConfirmNum() + "/Pay";
          }


        return "redirect:/Payment/" + r.getConfirmNum() + "/Pay";
    }
    @ModelAttribute("roomList")
    public Map<String, Object> getRoomList()
    {
        Map<String, Object> roomList = new HashMap<String, Object>();
        roomList.put("rooms", roomService.findAllRooms());

        return roomList;
    }

    @GetMapping("/Payment/{id}/Pay")
    public String GetPaymentPage(@PathVariable long id, Model model)
    {
        Reservation rser = reservationService.FindReservationByConfirmNum(id);
        Reservation r = new Reservation();
        Person p = new Person();
        Long DiffD = DiffInDays(rser.getStartDate(),rser.getEndDate());
        double Price = rser.getPrice() * DiffD;

        //12343
        ReservationDetailForm _rdt = new ReservationDetailForm();
        r = reservationService.findByConfirmationNum(id);
        r.setPrice(Price);
        Room room = roomService.findById( r.getRoom().getRoomNum());
        p = personService.findByPersonID((int) rser.getCustomer().getCustId());
        System.out.println("Address"  + p.getAddress());
        System.out.println("Type"+ r.getRoom().getType());
        model.addAttribute("reservation",reservationService.findByConfirmationNum(id));
        model.addAttribute("ReservationDetailForm",_rdt);
        model.addAttribute("person",p);
        model.addAttribute("room",r);

        model.addAttribute("amount", Price * 100); // in cents
        model.addAttribute("stripePublicKey", "pk_test_51MDR8MKnML8xwKNEdSs38rpZJKUsHLJVomUWEuFEya62o3rfQst9OFW1a3JS8tgsLKYMGEr5MvmkOH7kEdoTbfUn00nf3R4nRQ");
        model.addAttribute("currency", ChargeRequest.Currency.USD);
        return "MakePayment";
    }

    public Long GenCustomerId()
    {
        Random r = new Random( System.currentTimeMillis() );
        return Long.valueOf(10000 + r.nextInt(20000));
    }

    public Long DiffInDays(LocalDate d1, LocalDate d2)
    {

        // Toolz
        Long daysDiff = ChronoUnit.DAYS.between(d1,d2);
        System.out.println("The number of days between dates: " + daysDiff);

       return daysDiff;
    }

    @PostMapping("/CheckReservations")
    public String CheckReservation(@Valid ReservationDetailForm _rdf, Model model) throws ParseException
    {
        ReservationDetailForm rdf = new ReservationDetailForm();
        Reservation r  = reservationService.findByConfirmationNum(1001L);
        Person p = new Person();
        Long DiffD = DiffInDays(r.getStartDate(),r.getEndDate());
        double Price = r.getPrice() * DiffD;
        r.setPrice(Price);
         // System.out.println("CustID"+ r.getCustomer().getCustId());
         System.out.println("Email"+ r.getCustomer().getPerson().getFirstname());
        model.addAttribute("ReservationDetailForms", new ReservationDetailForm());
        model.addAttribute("reservations", r);
        model.addAttribute("person", r.getCustomer().getPerson());
        return "CheckReservation";

    }

    @PostMapping("/SearchReserv")
    public String SearchRecord(@Valid ReservationDetailForm rdt,  Model model) throws ParseException {
        Person p = new Person();
        model.addAttribute("ReservationDetailForms", new ReservationDetailForm());
        model.addAttribute("person", p);

        //return "redirect:/SearchReservation";
        return "SearchReservation";
    }

    @GetMapping("/Search")
    public String GetSearchReservation(Model model,ReservationDetailForm rdt)
    {

        model.addAttribute("ReservationDetailForms", new ReservationDetailForm());

        return "SearchReservation";
    }
}
