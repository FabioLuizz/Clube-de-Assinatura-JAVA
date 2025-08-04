/*
package com.projeto.clube_assinatura.controller;

import com.projeto.clube_assinatura.model.Plan;
import com.projeto.clube_assinatura.model.User;
import com.projeto.clube_assinatura.repository.PlanRepository;
import com.projeto.clube_assinatura.repository.UserRepository;
import com.projeto.clube_assinatura.service.PaymentService;
import com.projeto.clube_assinatura.service.PlanService;
import com.projeto.clube_assinatura.util.AuthUtil;
import com.stripe.exception.StripeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("payment")
public class PaymentController {

    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    private final PaymentService paymentService;

    public PaymentController (PlanRepository planRepository,
                                    UserRepository userRepository,
                                    PaymentService paymentService) {
        this.planRepository = planRepository;
        this.userRepository = userRepository;
        this.paymentService = paymentService;
    }

    @PostMapping("/checkout/{id}")
    public ResponseEntity<?> checkout(@PathVariable Long id) throws StripeException {

        try {
            String email = AuthUtil.getAuthentication();

            User user = userRepository.findByEmail(email).
                    orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

            Plan plan = planRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Plano não encontrado"));

            String checkoutUrl = paymentService.createCheckoutSession(user, plan);

            Map<String, String> response = new HashMap<>();
            response.put("checkoutUrl", checkoutUrl);
            return ResponseEntity.ok(response);


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("erro", e.getMessage()));
        }

    }

}
*/
