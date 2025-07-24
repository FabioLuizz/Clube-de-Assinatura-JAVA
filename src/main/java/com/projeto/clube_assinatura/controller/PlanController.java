package com.projeto.clube_assinatura.controller;

import com.projeto.clube_assinatura.dto.PlanDTO;
import com.projeto.clube_assinatura.model.Plan;
import com.projeto.clube_assinatura.repository.PlanRepository;
import com.projeto.clube_assinatura.service.PlanService;
import jakarta.validation.Valid;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plan")
public class PlanController {

    private PlanService planService;

    @Autowired
    public PlanController(PlanService planService ) {
        this.planService = planService;
    }

    @PostMapping
    public ResponseEntity<Plan> createPlan(@Valid @RequestBody PlanDTO planDTO) {
        Plan save = planService.planRegister(planDTO);
        return ResponseEntity.ok(save);

    }

    @GetMapping
    public ResponseEntity<List<Plan>> getAllPlans() {
        List<Plan> plans = planService.getAllPlans();
        return ResponseEntity.ok(plans);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Plan> deletePlan(@PathVariable Long id) {
       planService.planDelete(id);

       return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plan> updatePlan(@PathVariable Long id, @Valid @RequestBody PlanDTO updatePlanDTO) {

        try {
            Plan planUpdate = planService.planUpdate(id, updatePlanDTO);
            return ResponseEntity.ok(planUpdate);
        } catch (RuntimeException e) {
            return ResponseEntity.ok().build();
        }

    }


}
