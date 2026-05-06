package com.vromita.incident_management_system.model;

public enum Priority {

    LOW(48),
    MEDIUM(24),
    HIGH(12),
    CRITICAL(4);

    private final long slaHours;
    public static final long AT_RISK_THRESHOLD = 6;

    Priority(long slaHours){
        this.slaHours=slaHours;
    }

    public long getSlaHours() {
        return slaHours;
    }
}