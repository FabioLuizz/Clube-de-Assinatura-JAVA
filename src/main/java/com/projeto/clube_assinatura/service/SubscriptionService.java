package com.projeto.clube_assinatura.service;

import com.projeto.clube_assinatura.dto.SubscriptionDTO;
import com.projeto.clube_assinatura.model.Plan;
import com.projeto.clube_assinatura.model.Subscription;
import com.projeto.clube_assinatura.model.User;
import com.projeto.clube_assinatura.repository.PlanRepository;
import com.projeto.clube_assinatura.repository.SubscriptionRepository;
import com.projeto.clube_assinatura.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class SubscriptionService {

    private final SubscriptionRepository subRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;

    public SubscriptionService(SubscriptionRepository subRepository,
                               UserRepository userRepository,
                               PlanRepository planRepository) {
        this.subRepository = subRepository;
        this.userRepository = userRepository;
        this.planRepository = planRepository;
    }

    public SubscriptionDTO createSubscription(SubscriptionDTO subDTO, User user, Plan plan) {

        Subscription sub = new Subscription();

        sub.setUser(user);
        sub.setPlan(plan);
        sub.setStartDate(subDTO.getStartDate());
        sub.setEndDate(subDTO.getEndDate());
        sub.setActive(subDTO.isActive());

        Subscription save = subRepository.save(sub);

        return toDTO(save);

    }

    public SubscriptionDTO toDTO(Subscription sub) {

        SubscriptionDTO dto = new SubscriptionDTO();

        dto.setId(sub.getId());
        dto.setUserId(sub.getUser().getId());
        dto.setPlanId(sub.getPlan().getId());
        dto.setStartDate(sub.getStartDate());
        dto.setEndDate(sub.getEndDate());
        dto.setActive(sub.isActive());

        return dto;
    }


    public SubscriptionDTO updateSubscription(Long id, SubscriptionDTO subDTO, User user, Plan plan) {

        Subscription subscription = subRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found subscription"));

        subscription.setUser(user);
        subscription.setPlan(plan);
        subscription.setStartDate(subDTO.getStartDate());
        subscription.setEndDate(subDTO.getEndDate());
        subscription.setActive(subDTO.isActive());

        Subscription update = subRepository.save(subscription);

        return convertToDTO(update);
    }

    private SubscriptionDTO convertToDTO(Subscription subscription) {

        SubscriptionDTO dto = new SubscriptionDTO();

        dto.setId(subscription.getId());
        dto.setUserId(subscription.getUser().getId());
        dto.setPlanId(subscription.getPlan().getId());
        dto.setStartDate(subscription.getStartDate());
        dto.setEndDate(subscription.getEndDate());
        dto.setActive(subscription.isActive());

        return dto;

    }

    public List<Map<String, Object>> getById(Long id) {

        return subRepository.findByUserId(id)
                .stream().map(sub -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", sub.getId());
                    map.put("UserId", sub.getUser().getId());
                    map.put("PlanId", sub.getPlan().getName());
                    map.put("Plan-name", sub.getPlan().getName());
                    map.put("Date-Start", sub.getStartDate());
                    map.put("End-Date", sub.getEndDate());
                    return map;
                }).collect(Collectors.toList());

    }

    public void delete(Long id) {
        subRepository.deleteById(id);
    }


}
