package com.digicart.subscription.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "merchant_subscriptions", schema = "subscription_svc")
public class MerchantSubscription {

    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "merchant_email", unique = true, nullable = false)
    private String merchantEmail;

    @Column(name = "plan_id", length = 36)
    private String planId;

    @Column(name = "renews_at")
    private Instant renewsAt;

    @Column(name = "available_days")
    private Integer availableDays = 0;

    @Column(name = "subscribed_at")
    private Instant subscribedAt;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @PrePersist
    void prePersist() {
        if (id == null) id = UUID.randomUUID().toString();
        if (createdAt == null) createdAt = Instant.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getMerchantEmail() { return merchantEmail; }
    public void setMerchantEmail(String merchantEmail) { this.merchantEmail = merchantEmail; }
    public String getPlanId() { return planId; }
    public void setPlanId(String planId) { this.planId = planId; }
    public Instant getRenewsAt() { return renewsAt; }
    public void setRenewsAt(Instant renewsAt) { this.renewsAt = renewsAt; }
    public Integer getAvailableDays() { return availableDays; }
    public void setAvailableDays(Integer availableDays) { this.availableDays = availableDays; }
    public Instant getSubscribedAt() { return subscribedAt; }
    public void setSubscribedAt(Instant subscribedAt) { this.subscribedAt = subscribedAt; }
    public Instant getCreatedAt() { return createdAt; }
}
