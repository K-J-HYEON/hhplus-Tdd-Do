package io.hhplus.tdd.service;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.dto.response.UserPointRespDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class UserPointServiceImpl implements UserPointService {

    private final UserPointTable userPointTable;

    public UserPointServiceImpl(UserPointTable userPointTable) {
        this.userPointTable = userPointTable;
    }

    // 1. 포인트 조회
    @Transactional(readOnly = true)
    public List<UserPointRespDto> point(Long id) {
        try {
            return Collections.singletonList(UserPointRespDto.of(userPointTable.selectById(id)));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // 2. 포인트 충전
    @Transactional
    public List<UserPointRespDto> charge(Long id, Long amount) {
        try {
            return Collections.singletonList(UserPointRespDto.of(userPointTable.insertOrUpdate(id, amount)));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    // 3. 포인트 사용
    @Transactional
    public List<UserPointRespDto> use(Long id, Long amount) {
        try {
            return Collections.singletonList(UserPointRespDto.of(userPointTable.insertOrUpdate(id, amount)));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}