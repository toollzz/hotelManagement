package edu.bowiestate.hotelManagement.StripePayment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CheckoutController {

   // @Value("${STRIPE_PUBLIC_KEY}")
   // private String stripePublicKey;

    @RequestMapping("/checkouts")
    public String checkout(Model model) {
        model.addAttribute("amount", 50 * 100); // in cents
        model.addAttribute("stripePublicKey", "pk_test_51MDR8MKnML8xwKNEdSs38rpZJKUsHLJVomUWEuFEya62o3rfQst9OFW1a3JS8tgsLKYMGEr5MvmkOH7kEdoTbfUn00nf3R4nRQ");
        model.addAttribute("currency", ChargeRequest.Currency.USD);
        return "checkouts";
    }
}