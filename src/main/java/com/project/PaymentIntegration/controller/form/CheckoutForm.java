package com.project.PaymentIntegration.controller.form;

import jakarta.validation.constraints.*;


public class CheckoutForm {

    @NotNull
    @Min(5)
    private Integer amount;
    @Size(min = 5, max = 200)
    private String comment;
    @Email
    private String email;
    @NotNull
    private String name;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
