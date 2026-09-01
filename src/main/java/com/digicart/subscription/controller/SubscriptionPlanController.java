package com.digicart.subscription.controller;

import com.digicart.subscription.dto.SubscriptionPlanDto;
import com.digicart.subscription.entity.SubscriptionPlan;
import com.digicart.subscription.service.SubscriptionPlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subscription/plans")
public class SubscriptionPlanController {

    private final SubscriptionPlanService service;

    public SubscriptionPlanController(SubscriptionPlanService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listPlans() {
        List<SubscriptionPlan> plans = service.findAll();
        return ResponseEntity.ok(Map.of("plans", plans));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionPlan> getPlan(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SubscriptionPlan> createPlan(
            @RequestHeader(value = "X-User-Role", required = false) String role,
            @RequestBody SubscriptionPlanDto.CreateRequest req) {
        if (!"superadmin".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SubscriptionPlan> updatePlan(
            @PathVariable String id,
            @RequestHeader(value = "X-User-Role", required = false) String role,
            @RequestBody SubscriptionPlanDto.UpdateRequest req) {
        if (!"superadmin".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(
            @PathVariable String id,
            @RequestHeader(value = "X-User-Role", required = false) String role) {
        if (!"superadmin".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
