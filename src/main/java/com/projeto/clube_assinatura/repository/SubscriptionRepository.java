package com.projeto.clube_assinatura.repository;

import com.projeto.clube_assinatura.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByUserId(Long id);

}
