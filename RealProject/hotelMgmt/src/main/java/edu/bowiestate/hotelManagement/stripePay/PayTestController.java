package edu.bowiestate.hotelManagement.stripePay;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PayTestController {

    @GetMapping("/checkout")
    public String TestPay(Model model)
    {

         return "checkout";

    }
}
