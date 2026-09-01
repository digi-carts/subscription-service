package com.digicart.subscription.controller;

import com.digicart.subscription.dto.MerchantSubscriptionDto;
import com.digicart.subscription.entity.MerchantSubscription;
import com.digicart.subscription.service.MerchantSubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subscription")
public class MerchantSubscriptionController {

    private final MerchantSubscriptionService service;

    public MerchantSubscriptionController(MerchantSubscriptionService service) {
        this.service = service;
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> status(
            @RequestHeader(value = "X-User-Email", required = false) String email) {
        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "X-User-Email header required"));
        }
        return ResponseEntity.ok(service.getStatus(email));
    }

    @PostMapping("/assign")
    public ResponseEntity<MerchantSubscription> assign(
            @RequestHeader(value = "X-User-Role", required = false) String role,
            @RequestBody MerchantSubscriptionDto.AssignRequest req) {
        if (!"superadmin".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(service.assign(req));
    }

    @GetMapping("/merchants")
    public ResponseEntity<Map<String, Object>> listMerchants(
            @RequestHeader(value = "X-User-Role", required = false) String role) {
        if (!"superadmin".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<MerchantSubscription> merchants = service.findAll();
        return ResponseEntity.ok(Map.of("merchants", merchants));
    }

    @GetMapping("/merchants/{email}")
    public ResponseEntity<MerchantSubscription> getMerchant(
            @PathVariable String email,
            @RequestHeader(value = "X-User-Role", required = false) String role) {
        if (!"superadmin".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(service.findByEmail(email));
    }
}
