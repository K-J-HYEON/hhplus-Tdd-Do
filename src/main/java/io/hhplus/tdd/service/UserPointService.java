package io.hhplus.tdd.service;

import io.hhplus.tdd.dto.response.UserPointRespDto;
import io.hhplus.tdd.point.UserPoint;

import java.util.List;

public interface UserPointService {
    List<UserPointRespDto> getUserPoint(Long id);

    List<UserPointRespDto> increaseUserPoint(Long id, Long amount);

    List<UserPointRespDto> decreaseUserPoint(Long id, Long amount);
}
