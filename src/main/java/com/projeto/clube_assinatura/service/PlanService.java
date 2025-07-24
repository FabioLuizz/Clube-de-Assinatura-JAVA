package com.projeto.clube_assinatura.service;

import com.projeto.clube_assinatura.dto.PlanDTO;
import com.projeto.clube_assinatura.model.Plan;
import com.projeto.clube_assinatura.repository.PlanRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    public PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }

    public Optional<Plan> getPlan(Long id) {
        return planRepository.findById(id);
    }

    public Plan planRegister(@Valid PlanDTO planDTO) {

        Plan plan = new Plan();

        plan.setName(planDTO.getName());
        plan.setDescription(planDTO.getDescription());
        plan.setPrice(planDTO.getPrice());
        plan.setDuration(planDTO.getDuration());

        plan.setAvailable(true);

        return planRepository.save(plan);
    }

    public void planDelete(Long id) {
        planRepository.deleteById(id);
    }

    public Plan planUpdate(Long id, @Valid PlanDTO planUpdateDTO) {

        return planRepository.findById(id)
                .map(plan -> {
                    plan.setName(planUpdateDTO.getName());
                    plan.setDescription(planUpdateDTO.getDescription());
                    plan.setPrice(planUpdateDTO.getPrice());
                    plan.setDuration(planUpdateDTO.getDuration());
                    return planRepository.save(plan);
                })
                .orElseThrow(() -> new RuntimeException("Plano não encontrado"));

    }



}
