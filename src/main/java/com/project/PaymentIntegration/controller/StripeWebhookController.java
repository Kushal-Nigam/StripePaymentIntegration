package com.project.PaymentIntegration.controller;


import com.project.PaymentIntegration.service.StripeWebhookService;
import com.stripe.exception.EventDataObjectDeserializationException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.*;
import com.stripe.net.Webhook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StripeWebhookController {

    @Autowired
    StripeWebhookService stripeWebhookService;

    private final Logger logger = LoggerFactory.getLogger(StripeWebhookController.class);

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @PostMapping("/stripe/events")
    public String handleStripeEvents(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {

        if(sigHeader == null){
            return "";
        }

        Event event;

                // Only verify the event if you have an endpoint secret defined.
                // Otherwise, use the basic event deserialized with GSON.
                try {
                    event = Webhook.constructEvent(
                            payload, sigHeader, endpointSecret
                    );
                } catch (SignatureVerificationException e) {
                    // Invalid signature
                    logger.info("⚠ Webhook error while validating signature.");
                    return HttpStatus.FORBIDDEN.toString();
                }

            // Deserialize the nested object inside the event
        
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            StripeObject stripeObject = null;
            if (dataObjectDeserializer.getObject().isPresent()) {
                stripeObject = dataObjectDeserializer.getObject().get();
            } else {
                throw new IllegalStateException(
                        String.format("Unable to deserialize event data object for %s", event));
            }
            // Handle the event
            switch (event.getType()) {
                case "payment_intent.succeeded":
                    PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
                    logger.info("Payment for id={}, amount={} succeeded.", paymentIntent.getId(), paymentIntent.getAmount() );
                    // Then define and call a method to handle the successful payment intent.
                    handlePaymentIntentSucceeded(paymentIntent);
                    break;
                case "charge.succeeded":
                    Charge charge = (Charge) stripeObject;
                    logger.info("Charge for id={}, amount={}, from country={} with comment ={} succeeded.", charge.getId(),charge.getAmount(), charge.getPaymentMethodDetails().getCard().getCountry(), charge.getMetadata().get("comment"));
                    handleChargeSucceeded(charge);
                    break;
                case "payment_intent.payment_failed":
                    PaymentIntent failedPaymentIntent = (PaymentIntent) stripeObject;
                    logger.info("Payment for id={}, amount={} failed due to reason={}.", failedPaymentIntent.getId(),failedPaymentIntent.getAmount(), failedPaymentIntent.getLastPaymentError().getMessage());
                    break;
                default:
                    logger.warn("Unhandled event type: {} " ,event.getType());
                    break;
            }
            return HttpStatus.OK.toString();

    }

    private void handleChargeSucceeded(Charge charge) {
        stripeWebhookService.chargeSucceeded(charge);
    }


    private void handlePaymentIntentSucceeded(PaymentIntent paymentIntent) {
        stripeWebhookService.paymentSucceeded(paymentIntent);
    }
}
