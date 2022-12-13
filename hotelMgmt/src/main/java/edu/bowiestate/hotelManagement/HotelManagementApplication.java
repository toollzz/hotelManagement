package edu.bowiestate.hotelManagement;

import com.stripe.Stripe;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HotelManagementApplication {
     public void setup()
	 {
		// Stripe.apiKey = "sk_test_mxDWPP3F9qbwe0LR1Ypwsiu7";
         Stripe.apiKey = "sk_test_51MDR8MKnML8xwKNE3TJZkYwo7JdMwEjUK24LpE211NHi33orbyy2bLtffYB0GEslNLO50MMExhi4tB8ABeQheREy00pVbS29Ag";
	}
	public static void main(String[] args) {
		SpringApplication.run(HotelManagementApplication.class, args);
	}

}
