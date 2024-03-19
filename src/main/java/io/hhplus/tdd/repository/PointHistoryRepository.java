package io.hhplus.tdd.repository;

import io.hhplus.tdd.point.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
    Optional<PointHistory> findById(final Long id);
}
