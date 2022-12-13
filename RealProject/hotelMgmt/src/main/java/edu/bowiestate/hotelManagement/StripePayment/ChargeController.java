package edu.bowiestate.hotelManagement.StripePayment;


import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import edu.bowiestate.hotelManagement.Error.CustomErrorController;
import edu.bowiestate.hotelManagement.reservation.ReservationDetailForm;
import edu.bowiestate.hotelManagement.reservation.ReservationRoomDto;
import edu.bowiestate.hotelManagement.room.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.security.sasl.AuthenticationException;
import java.util.List;


@Controller
public class ChargeController {


    @Autowired
    private StripeService paymentsService;

    @PostMapping("/charge")
    public String charge(ChargeRequest chargeRequest, Model model)
            throws StripeException, AuthenticationException {
        try {
            chargeRequest.setDescription("Example charge");
            chargeRequest.setCurrency(ChargeRequest.Currency.USD);
            Charge charge = paymentsService.charge(chargeRequest);
            model.addAttribute("id", charge.getId());
            model.addAttribute("status", charge.getStatus());
            model.addAttribute("chargeId", charge.getId());
            model.addAttribute("balance_transaction", charge.getBalanceTransaction());
            System.out.println("status");

            return "redirect:result";
        }
        catch (Exception e) {
          e.getMessage();
        }
        return  null;
    }

    @GetMapping(value= "/Confirmation")
    public String MakeReservation(Model model, ReservationRoomDto rdt) {

     //   model.addAttribute("error", ex.getMessage());

        return  "result";
    }

    @GetMapping(value= "/result")
    public String result(){
          return "result";
    }

  /*  @GetMapping(value= "/result")
    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }*/
}