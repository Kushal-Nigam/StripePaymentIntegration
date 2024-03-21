package com.project.PaymentIntegration.repository;

import com.project.PaymentIntegration.entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentDetails,Integer> {
}
