package edu.bowiestate.hotelManagement.StripePayment;

import com.stripe.Stripe;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.security.sasl.AuthenticationException;
import java.util.HashMap;
import java.util.Map;



    @Service
    public class StripeService {

      //  @Value("${STRIPE_SECRET_KEY}")
       // private String secretKey;

        @PostConstruct
        public void init() {
         //   Stripe.apiKey = secretKey;
            Stripe.apiKey = "sk_test_51MDR8MKnML8xwKNE3TJZkYwo7JdMwEjUK24LpE211NHi33orbyy2bLtffYB0GEslNLO50MMExhi4tB8ABeQheREy00pVbS29Ag";

        }
        public Charge charge(ChargeRequest chargeRequest)
                throws AuthenticationException, StripeException{
             //   APIConnectionException, APIException {
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", chargeRequest.getAmount());
            chargeParams.put("currency", chargeRequest.getCurrency());
            chargeParams.put("description", chargeRequest.getDescription());
            chargeParams.put("source", chargeRequest.getStripeToken());
            return Charge.create(chargeParams);
        }
    }

