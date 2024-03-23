package io.hhplus.tdd.service;

import io.hhplus.tdd.TestException;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.UserPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static io.hhplus.tdd.TestErrorType.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserPointServiceTest {

    @InjectMocks
    UserPointService sut;

    @Mock
    UserPointTable userPointTable;

    /** charge
     * 1. 충전포인트가 음수이면 예외를 반환
     * 2. userPoint가 없으면 예외를 반환
     * 3. userPointTable.insert가 정상적으로 결과를 반환하면 그 결과를 그대로 반환
     */

    @Test
    @DisplayName("충전포인트가 음수이면 예외를 반환")
    void charge1() throws InterruptedException {
        long userPointId = 1;
        when(userPointTable.selectById(userPointId)).thenReturn(UserPoint.empty(userPointId));
        Exception e = null;

        try {
            UserPoint userPoint = sut.charge(userPointId, -1000);
        } catch (Exception exception) {
            e = exception;
        }
        assert e != null;
        assert e instanceof TestException;
        assert ((TestException) e).getMessage().equals(CHARGE_POINS_IS_NEGATIVE);
    }

    @Test
    @DisplayName("userPoint가 없으면 예외를 반환")
    void charge2() throws InterruptedException {
        long userPointId = 1;
        when(userPointTable.selectById(userPointId)).thenReturn(null);
        Exception e = null;

        try {
            UserPoint userPoint = sut.charge(userPointId, 1000);
        } catch (Exception exception) {
            e = exception;
        }

        assert e != null;
        assert e instanceof TestException;
        assert ((TestException) e).getMessage().equals(POINT_NOT_FOUND);
    }

    @Test
    @DisplayName("userPointTable.insert가 정상적으로 결과를 반환하면 그 결과를 그대로 반환")
    void charge3() throws InterruptedException {
        long userPointId = 1;
        when(userPointTable.selectById(userPointId)).thenReturn(new UserPoint(userPointId, 10000, 0));
        UserPoint savedUserPoint = new UserPoint(userPointId, 8000, 0);
        when(userPointTable.insertOrUpdate(anyLong(), anyLong())).thenReturn(savedUserPoint);
        UserPoint result = sut.charge(1, 1000);
        assert result.equals(savedUserPoint);
    }



    /** use
     * 1. userPoint가 없으면 예외를 반환.
     * 2. userPoint가 사용금액보다 적으면 예외를 반환.
     * 3. 잔액이 반영된 userPoint는 userPointTable에 저장.
     * 4. userPointTable.insert가 정상적으로 결과를 반환하면 그 결과를 그대로 반환.
     * 5. userPointTable.insert가 예외를 던지면 예외를 반환.
     */

    @Test
    @DisplayName("userPoint가 없으면 예외를 반환")
    void use1() throws InterruptedException {
        long userPointId = 1;
        when(userPointTable.selectById(userPointId)).thenReturn(null);
        Exception e = null;

        try {
            UserPoint userPoint = sut.use(userPointId, 1000);
        } catch (Exception exception) {
            e = exception;
        }

        assert e != null;
        assert e instanceof TestException;
        assert ((TestException) e).getMessage().equals(POINT_NOT_FOUND);
    }

    @Test
    @DisplayName("userPoint가 사용금액보다 적으면 예외를 반환한다.")
    void use2() throws InterruptedException {
        long userPointId = 1;
        when(userPointTable.selectById(userPointId)).thenReturn(UserPoint.empty(userPointId));
        Exception e = null;

        try {
            UserPoint userPoint = sut.use(userPointId, 1000);
        } catch (Exception exception) {
            e = exception;
        }

        assert e != null;
        assert e instanceof TestException;
        assert ((TestException) e).getMessage().equals(NOT_ENOUGH_MONEY);
    }

    @Test
    @DisplayName("userPointTable.insert가 정상적으로 결과를 반환하면 그 결과를 그대로 반환")
    void use3() throws InterruptedException {
        long userPointId = 1;
        when(userPointTable.selectById(userPointId)).thenReturn(new UserPoint(userPointId, 10000, 0));
        UserPoint savedUserPoint = new UserPoint(userPointId, 8000, 0);
        when(userPointTable.insertOrUpdate(anyLong(), anyLong())).thenReturn(savedUserPoint);
        UserPoint result = sut.use(1, 1000);
        assert result.equals(savedUserPoint);
    }
}