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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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

        Long userId = 1L;
        Long point = 1L;
        Long updateMills = 1L;

        UserPoint expectResult = new UserPoint(userId, point, updateMills);
        List<UserPointRespDto> result = userPointService.getUserPoint(1L);

        assertThat(result.equals(userId)).isEqualTo(expectResult.equals(userId));
        assertThat(result.equals(point)).isEqualTo(expectResult.equals(point));
    }

    @Test
    @DisplayName("사용자 포인트 충전/이용 내역 조회")
    void getPointHistoryTest() {

        Long userId = 1L;
        Long amount = 1L;
        UserPointReqDto userPointReqDto = new UserPointReqDto(amount);
        userPointService.increaseUserPoint(userId, userPointReqDto.point());

        List<PointHistory> userPointRespDto = pointHistoryService.getUserPointHistory(userId);

        assertThat(userPointRespDto).isNotEmpty();
    }

    @Test
    @DisplayName("사용자 포인트 충전성공")
    void increaseUserPoint() {
        UserPoint userPoint = new UserPoint(1L, 10L, System.currentTimeMillis());
        assertDoesNotThrow(() -> {
            userPointService.increaseUserPoint(userPoint.id(), userPoint.point());
        });
    }

    @Test
    @DisplayName("사용자 포인트 충전실패 Case 1: ID가 null이거나 포인트가 null 인경우")
    void increaseUserPointFailCaseOne() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    userPointService.increaseUserPoint(null, null);
                });
    }

    @Test
    @DisplayName("사용자 포인트 충전실패 Case 2: 충전 point가 0 이하일 경우")
    void increaseUserPointFailCaseTwo() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    userPointService.increaseUserPoint(1L, -1000L);
                });
    }

    @Test
    @DisplayName("ID와 잔액이 존재할 때 사용자 포인트 사용 성공")
    void decreaseUserPointTest() {
        UserPoint userPoint = new UserPoint(1L, 50L, System.currentTimeMillis());
        assertDoesNotThrow(() -> {
            userPointService.decreaseUserPoint(userPoint.id(), userPoint.point());
        });
    }

    @Test
    @DisplayName("포인트 사용 시 포인트 업데이트")
    void decreaseUserPointUpdateTest() {
        Long chargePoint = 500L;
        UserPoint userPoint = new UserPoint(1L, chargePoint, System.currentTimeMillis());
        List<UserPointRespDto> chargeUserPoint = userPointService.increaseUserPoint(userPoint.id(), userPoint.point());

        Long usePoint = 100L;
        List<UserPointRespDto> usedPoint = userPointService.decreaseUserPoint(userPoint.id(), userPoint.point());
        assertThat(usedPoint).isEqualTo(chargePoint - usePoint);
    }

    @Test
    @DisplayName("포인트 사용 실패 Case 1: ID 또는 사용 포인트가 null")
    void decreaseUserPointFailCaseOne() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    userPointService.decreaseUserPoint(null, null);
                }).withMessageContaining("ID가 null 이거나 point가 null입니다.");
    }

    @Test
    @DisplayName("포인트 사용 실패 Case 2: 사용 포인트 > 잔액 포인트")
    void decreaseUserPointFailCaseTwo() {
        UserPoint userPoint = new UserPoint(1L, 100L, System.currentTimeMillis());
        List<UserPointRespDto> chargedUserPoint = userPointService.increaseUserPoint(userPoint.id(), userPoint.point());

        Long useUserPoint = 1000L;
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    // 수정해야함 - chargedUserPoint를 반영해야 함
                    userPointService.decreaseUserPoint(userPoint.id(), useUserPoint);
                }).withMessageContaining("포인트 잔액이 부족합니다.");
    }
}