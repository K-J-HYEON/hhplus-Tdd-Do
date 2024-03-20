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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class UserPointServiceTest {

    @Autowired
    private UserPointServiceImpl userPointService;

    @Autowired
    private PointHistoryServiceImpl pointHistoryService;

    @Autowired
    private UserPointTable userPointTable;

    @BeforeEach
    void setUp() {
        UserPointTable userPointTable = new UserPointTable();
        PointHistoryTable pointHistoryTable = new PointHistoryTable();
        userPointService = new UserPointServiceImpl(userPointTable);
        pointHistoryService = new PointHistoryServiceImpl(pointHistoryTable);
    }

    @Test
    @Transactional
    @DisplayName("사용자 포인트 조회")
    void pointTest() {

        Long userId = 1L;
        Long point = 1L;
        Long updateMills = 1L;

        UserPoint expectResult = new UserPoint(userId, point, updateMills);
        List<UserPointRespDto> result = userPointService.use(1L, 1000L);

        assertThat(result.equals(userId)).isEqualTo(expectResult.equals(userId));
        assertThat(result.equals(point)).isEqualTo(expectResult.equals(point));
    }

    @Test
    @Transactional
    @DisplayName("사용자 포인트 충전/이용 내역 조회")
    void pointHistoryTest() {

        Long userId = 1L;
        Long amount = 1L;
        UserPointReqDto userPointReqDto = new UserPointReqDto(amount);
        userPointService.charge(userId, userPointReqDto.point());

        List<PointHistory> userPointRespDto = pointHistoryService.history(userId);

        assertThat(userPointRespDto).isNotEmpty();
    }

    @Test
    @Transactional
    @DisplayName("사용자 포인트 충전성공")
    void chargeTest() {
        UserPoint userPoint = new UserPoint(1L, 10L, System.currentTimeMillis());
        assertDoesNotThrow(() -> {
            userPointService.charge(userPoint.id(), userPoint.point());
        });
    }

    @Test
    @Transactional
    @DisplayName("사용자 포인트 충전실패 Case 1: ID가 null이거나 포인트가 null 인경우")
    void chargeFailCaseOneTest() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    userPointService.charge(null, null);
                });
    }

    @Test
    @Transactional
    @DisplayName("사용자 포인트 충전실패 Case 2: 충전 point가 0 이하일 경우")
    void chargeFailCaseTwoTest() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    userPointService.charge(1L, -1000L);
                });
    }

    @Test
    @Transactional
    @DisplayName("ID와 잔액이 존재할 때 사용자 포인트 사용 성공")
    void useTest() {
        UserPoint userPoint = new UserPoint(1L, 50L, System.currentTimeMillis());
        assertDoesNotThrow(() -> {
            userPointService.use(userPoint.id(), userPoint.point());
        });
    }

    @Test
    @DisplayName("포인트 사용 시 포인트 업데이트")
    void usePointUpdateTest() {
        Long chargePoint = 500L;
        UserPoint userPoint = new UserPoint(1L, chargePoint, System.currentTimeMillis());
        List<UserPointRespDto> chargeUserPoint = userPointService.charge(userPoint.id(), userPoint.point());

        Long usePoint = 100L;
        List<UserPointRespDto> usedPoint = userPointService.use(userPoint.id(), userPoint.point());
        assertThat(usedPoint).isEqualTo(chargePoint - usePoint);
    }

    @Test
    @DisplayName("포인트 사용 실패 Case 1: ID 또는 사용 포인트가 null")
    void useFailCaseOneTest() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    userPointService.use(null, null);
                }).withMessageContaining("ID가 null 이거나 point가 null입니다.");
    }

    @Test
    @DisplayName("포인트 사용 실패 Case 2: 사용 포인트 > 잔액 포인트")
    void useFailCaseTwoTest() {
        UserPoint userPoint = new UserPoint(1L, 100L, System.currentTimeMillis());
        List<UserPointRespDto> chargedUserPoint = userPointService.charge(userPoint.id(), userPoint.point());

        Long useUserPoint = 1000L;
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    // 수정해야함 - chargedUserPoint를 반영해야 함
                    userPointService.use(userPoint.id(), useUserPoint);
                }).withMessageContaining("포인트 잔액이 부족합니다.");

    }

    // 동시에 여러 건의 포인트 충전 요청이 들어올 경우 순차적으로 처리
    @Test
    @Transactional
    @DisplayName("같은 사용자가 동시 여러 건의 포인트가 순차적으로 충전")
    void charge100Req() throws InterruptedException {

        // given
        final int threadCount = 100;
        // 고정 스레드 풀 32개 생성
        final ExecutorService executorService = Executors.newFixedThreadPool(32);
        // 설정한 스레드 개수 100개만큼의 count 가진 CountDownLatch 생성
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    userPointService.charge(1L, 1000L);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        // 수정 필요 orElseThrow 추가 해야할듯
        countDownLatch.await();
        final UserPoint userPoint = userPointTable.insertOrUpdate(1L, 1000L);

        // then
        assertThat(userPoint.point()).isEqualTo(0);
    }


    // 동시에 여러 건의 포인트 이용 요청이 들어올 경우 순차적으로 처리
    @Test
    @Transactional
    @DisplayName("같은 사용자가 동시 여러 건의 포인트가 순차적으로 처리")
    void use100Req() throws InterruptedException {

        // given
        final int threadCount = 100;
        // 고정 스레드 풀 32개 생성
        final ExecutorService executorService = Executors.newFixedThreadPool(32);
        // 설정한 스레드 개수 100개만큼의 count 가진 CountDownLatch 생성
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    userPointService.use(1L, 1000L);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        // 수정 필요
        countDownLatch.await();
        final UserPoint userPoint = userPointTable.insertOrUpdate(1L, -1000L);

        // then
        assertThat(userPoint.point()).isEqualTo(0);
    }
}