package com.project.PaymentIntegration.controller;

import com.project.PaymentIntegration.controller.form.CheckoutForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController {

    @Value("${stripe.public.key}")
    private String stripePublicKey;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("checkoutForm", new CheckoutForm());
        return "index";
    }

    @PostMapping("/")
    public String checkout(@ModelAttribute @Valid CheckoutForm checkoutForm, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return"index";
        }
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("amount", checkoutForm.getAmount());
        model.addAttribute("email", checkoutForm.getEmail());
        model.addAttribute("comment", checkoutForm.getComment());
        model.addAttribute("name", checkoutForm.getName());
        return"checkout";
    }

    @GetMapping("/payment_completion/{name}/{amount}")
    public String payment_completion(@PathVariable("name") String name,@PathVariable Integer amount, Model model){
        model.addAttribute("amount", amount);
        model.addAttribute("name", name);
        return "completion";
    }
}
