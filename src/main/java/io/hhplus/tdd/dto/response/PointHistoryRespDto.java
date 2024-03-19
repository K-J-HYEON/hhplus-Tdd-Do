package io.hhplus.tdd.dto.response;

import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;

import java.util.List;

public record PointHistoryRespDto(
        Long id,
        Long userId,
        TransactionType type,
        Long amount,
        Long timeMillis
) {
    public static PointHistoryRespDto of(PointHistory pointHistory) {
        return new PointHistoryRespDto(
                pointHistory.id(),
                pointHistory.userId(),
                pointHistory.type(),
                pointHistory.amount(),
                pointHistory.timeMillis()
        );
    }
}
