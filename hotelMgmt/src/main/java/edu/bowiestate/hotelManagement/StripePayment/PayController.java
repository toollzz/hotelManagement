package edu.bowiestate.hotelManagement.StripePayment;

import com.google.gson.annotations.SerializedName;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayController {

    static class CreatePayment {
        @SerializedName("items")
        Object[] items;

        public Object[] getItems() {
            return items;
        }
    }

    static class CreatePaymentResponse {
        private String clientSecret;
        public CreatePaymentResponse(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }
    }

    @PostMapping("/create-payment-intent")
    public CreatePaymentResponse  creatPaymentIntent(@RequestBody CreatePayment createPayment) throws StripeException {


           // CreatePayment postBody = gson.fromJson(request.body(), CreatePayment.class);
            PaymentIntentCreateParams params =PaymentIntentCreateParams.builder()
                            .setAmount(15 * 100L)
                            .setCurrency("usd")
                            .build();

            // Create a PaymentIntent with the order amount and currency
            PaymentIntent paymentIntent = PaymentIntent.create(params);

          return new CreatePaymentResponse(paymentIntent.getClientSecret());
           // return gson.toJson(paymentResponse);


    }
}

