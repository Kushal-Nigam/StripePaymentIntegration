package com.project.PaymentIntegration.service;

import com.project.PaymentIntegration.entity.ChargeDetails;
import com.project.PaymentIntegration.entity.PaymentDetails;
import com.project.PaymentIntegration.repository.ChargeRepository;
import com.project.PaymentIntegration.repository.PaymentRepository;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StripeWebhookService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ChargeRepository chargeRepository;


    public void paymentSucceeded(PaymentIntent paymentIntent) {
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setAmount(paymentIntent.getAmount());
        paymentDetails.setEmail(paymentIntent.getReceiptEmail());
        paymentDetails.setPaymentIntentId(paymentIntent.getId());
        paymentDetails.setCurrency(paymentIntent.getCurrency());
        paymentDetails.setCreated(paymentIntent.getCreated());
        paymentRepository.save(paymentDetails);
    }

    public void chargeSucceeded(Charge charge) {
        ChargeDetails chargeDetails = new ChargeDetails();
        chargeDetails.setChargeId(charge.getId());
        chargeDetails.setPaymentIntentId(charge.getPaymentIntent());
        chargeDetails.setComment(charge.getMetadata().get("comment"));
        chargeDetails.setAmount(charge.getAmount());
        chargeDetails.setEmail(charge.getBillingDetails().getEmail());
        chargeDetails.setName(charge.getBillingDetails().getName());
        chargeDetails.setCountryCode(charge.getPaymentMethodDetails().getCard().getCountry());
        chargeRepository.save(chargeDetails);
    }
}
