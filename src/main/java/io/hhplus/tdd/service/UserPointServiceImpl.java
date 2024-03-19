package io.hhplus.tdd.service;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.dto.response.UserPointRespDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public List<UserPointRespDto> getUserPoint(Long id) {
        try {
            return Collections.singletonList(UserPointRespDto.of(userPointTable.selectById(id)));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // 2. 포인트 충전
    public List<UserPointRespDto> increaseUserPoint(Long id, Long amount) {
        try {
            return Collections.singletonList(UserPointRespDto.of(userPointTable.insertOrUpdate(id, amount)));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    // 3. 포인트 사용
    public List<UserPointRespDto> decreaseUserPoint(Long id, Long amount) {
        try {
            return Collections.singletonList(UserPointRespDto.of(userPointTable.insertOrUpdate(id, amount)));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
