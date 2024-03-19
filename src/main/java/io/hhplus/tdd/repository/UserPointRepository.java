package io.hhplus.tdd.repository;

import io.hhplus.tdd.point.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface UserPointRepository extends JpaRepository<UserPoint, Long> {
    Optional<UserPoint> findById(Long id);
    List<UserPoint> findAll(Long id);
}
