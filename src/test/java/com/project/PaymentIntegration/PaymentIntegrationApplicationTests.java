package com.project.PaymentIntegration;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PaymentIntegrationApplicationTests {

	@Value("${stripe.api.key}")
	private String stripeApiKey;

	@PostConstruct
	public void setup(){
		Stripe.apiKey = stripeApiKey;
	}

	@Test
	void contextLoads() {
	}

}
