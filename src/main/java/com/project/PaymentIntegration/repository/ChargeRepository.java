package com.project.PaymentIntegration.repository;

import com.project.PaymentIntegration.entity.ChargeDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargeRepository extends JpaRepository<ChargeDetails,Integer> {
}
