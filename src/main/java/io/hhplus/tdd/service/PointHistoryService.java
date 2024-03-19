package io.hhplus.tdd.service;

import io.hhplus.tdd.point.PointHistory;

import java.util.List;

public interface PointHistoryService {
    List<PointHistory> getPointHistory(Long id);
}
