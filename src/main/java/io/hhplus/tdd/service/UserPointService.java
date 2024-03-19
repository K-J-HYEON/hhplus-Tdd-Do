package io.hhplus.tdd.service;

import io.hhplus.tdd.dto.response.UserPointRespDto;

import java.util.List;

public interface UserPointService {
    List<UserPointRespDto> point(Long id);

    List<UserPointRespDto> charge(Long id, Long amount);

    List<UserPointRespDto> use(Long id, Long amount);
}
