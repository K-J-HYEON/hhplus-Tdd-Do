package io.hhplus.tdd.point;

import jakarta.persistence.Entity;

@Entity
public enum TransactionType {
    CHARGE,
    USE,
}
