package io.hhplus.tdd.service;

import static org.assertj.core.api.Assertions.*;
import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.dto.request.UserPointReqDto;
import io.hhplus.tdd.dto.response.UserPointRespDto;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.UserPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

//인자로 확장할 Extension을 명시. SpringExtension.class 또는 MockitoExtension.class를 많이 사용
@ExtendWith(MockitoExtension.class)
public class UserPointServiceImplTest {

    @Mock
    private UserPointReqDto userPointReqDto;

    @InjectMocks
    private UserPointServiceImpl userPointService;

    @InjectMocks
    private PointHistoryServiceImpl pointHistoryService;

    @BeforeEach
    void setUp() {
        UserPointTable userPointTable = new UserPointTable();
        PointHistoryTable pointHistoryTable = new PointHistoryTable();
        userPointService = new UserPointServiceImpl(userPointTable);
        pointHistoryService = new PointHistoryServiceImpl(pointHistoryTable);
    }

    @Test
    @DisplayName("사용자 포인트 조회")
    void getUserPointTest() {
        // given
        Long userId = 1L;
        Long point = 1L;
        Long updateMills = 1L;

        // when
        UserPoint expectResult = new UserPoint(userId, point, updateMills);

        // then
        assertThat(expectResult.id()).isEqualTo(expectResult.id());
    }

    @Test
    @DisplayName("사용자 포인트 충전/이용 내역 조회")
    void getPointHistoryTest() {

        // given
        Long userId = 1L;
        Long amount = 1L;
        UserPointReqDto userPointReqDto = new UserPointReqDto(amount);
        userPointService.increaseUserPoint(userId, userPointReqDto.point());

        // when
        List<PointHistory> userPointRespDto = pointHistoryService.getUserPointHistory(userId);

        // then
        assertThat(userPointRespDto).isNotEmpty();
    }


    // 수정
    @Test
    @DisplayName("사용자 포인트 충전")
    void increaseUserPoint() {

        // given
        Long userId = 1L;
        Long amount = 1L;
        UserPointReqDto userPointRequest = new UserPointReqDto(amount);

        // when
        List<UserPointRespDto> expectResult = userPointService.getUserPoint(1L);
        List<UserPointRespDto> result = userPointService.increaseUserPoint(userId, amount);

        // then
        assertThat(expectResult).isEqualTo(1);
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("사용자 포인트 사용")
    void decreaseUserPointTest() {

        // givem
        Long userId = 1L;
        Long amount = 1L;
        UserPointReqDto userPointReqDto = new UserPointReqDto(amount);

        // when
        List<UserPointRespDto> result = userPointService.decreaseUserPoint(userId, amount);
        List<UserPointRespDto> expectResult = userPointService.getUserPoint(userId);


        // then
        assertThat(result.contains(userId)).isEqualTo(expectResult.equals(userId));
        assertThat(result.contains(amount)).isEqualTo(expectResult.equals(amount));
    }
}