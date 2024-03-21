package com.project.PaymentIntegration.dto;

import com.google.gson.annotations.SerializedName;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreatePayment {

    @NotNull
    @Min(5)
    private Integer amount;

    @Size(min = 5, max = 200)
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
