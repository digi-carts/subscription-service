package com.digicart.subscription.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "subscription_plans", schema = "subscription_svc")
public class SubscriptionPlan {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(name = "max_products")
    private Integer maxProducts = 50;

    private Double price = 0.0;

    private String currency = "INR";

    @Enumerated(EnumType.STRING)
    @Column(name = "billing_period", length = 50)
    private BillingPeriod billingPeriod = BillingPeriod.MONTHLY;

    @Column(name = "custom_days")
    private Integer customDays;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> features;

    @Column(columnDefinition = "TEXT")
    private String details;

    private String level;

    @Column(name = "max_uses")
    private Integer maxUses;

    @Column(name = "total_uses")
    private Integer totalUses = 0;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @PrePersist
    void prePersist() {
        if (id == null) id = UUID.randomUUID().toString();
        if (createdAt == null) createdAt = Instant.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getMaxProducts() { return maxProducts; }
    public void setMaxProducts(Integer maxProducts) { this.maxProducts = maxProducts; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public BillingPeriod getBillingPeriod() { return billingPeriod; }
    public void setBillingPeriod(BillingPeriod billingPeriod) { this.billingPeriod = billingPeriod; }
    public Integer getCustomDays() { return customDays; }
    public void setCustomDays(Integer customDays) { this.customDays = customDays; }
    public Map<String, Object> getFeatures() { return features; }
    public void setFeatures(Map<String, Object> features) { this.features = features; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public Integer getMaxUses() { return maxUses; }
    public void setMaxUses(Integer maxUses) { this.maxUses = maxUses; }
    public Integer getTotalUses() { return totalUses; }
    public void setTotalUses(Integer totalUses) { this.totalUses = totalUses; }
    public Instant getCreatedAt() { return createdAt; }
}
