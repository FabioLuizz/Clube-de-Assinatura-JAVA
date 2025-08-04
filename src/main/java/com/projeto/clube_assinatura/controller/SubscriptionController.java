package com.projeto.clube_assinatura.controller;

import com.projeto.clube_assinatura.dto.SubscriptionDTO;
import com.projeto.clube_assinatura.model.Plan;
import com.projeto.clube_assinatura.model.Subscription;
import com.projeto.clube_assinatura.model.User;
import com.projeto.clube_assinatura.repository.PlanRepository;
import com.projeto.clube_assinatura.repository.UserRepository;
import com.projeto.clube_assinatura.service.PaymentService;
import com.projeto.clube_assinatura.service.SubscriptionService;
import com.projeto.clube_assinatura.util.AuthUtil;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sub")
public class SubscriptionController {

    private final SubscriptionService subService;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final PaymentService paymentService;

    public SubscriptionController(UserRepository userRepository, PlanRepository planRepository, SubscriptionService subService, PaymentService paymentService) {
        this.userRepository = userRepository;
        this.planRepository = planRepository;
        this.subService = subService;
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createSub(@RequestBody @Valid SubscriptionDTO subDTO) throws StripeException {

        String userAuth = AuthUtil.getAuthentication();

        User user = userRepository.findByEmail(userAuth)
                .orElseThrow(() -> new RuntimeException("Not found user"));

        Plan plan = planRepository.findById(subDTO.getPlanId())
                .orElseThrow(() -> new RuntimeException("Not found plan"));

        SubscriptionDTO subscription = subService.createSubscription(subDTO, user, plan);

        String checkoutUrl = paymentService.createCheckoutSession(user, plan, subscription);

        Map<String, String> response = new HashMap<>();
        response.put("checkoutUrl", checkoutUrl);

        return ResponseEntity.ok(response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> updateSubscription(@PathVariable Long id, @RequestBody @Valid SubscriptionDTO subDTO) {

        String userAuth = AuthUtil.getAuthentication();

        User user = userRepository.findByEmail(userAuth)
                .orElseThrow(() -> new RuntimeException("Not found user"));

        Plan plan = planRepository.findById(subDTO.getPlanId())
                .orElseThrow(() -> new RuntimeException("Not found plan"));

        SubscriptionDTO subscription = subService.updateSubscription(id, subDTO, user, plan);

        return ResponseEntity.status(HttpStatus.CREATED).body(subscription);

    }

    @GetMapping("/{id}")
    public List<Map<String, Object>> getAll(@PathVariable Long id) {
        return subService.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSub(@PathVariable Long id) {
        subService.delete(id);

        return ResponseEntity.ok("Plano cancelado com sucesso");
    }

}
