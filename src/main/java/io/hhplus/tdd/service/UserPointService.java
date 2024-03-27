package io.hhplus.tdd.service;

import io.hhplus.tdd.TestErrorType;
import io.hhplus.tdd.TestException;
import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.UserPoint;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserPointService {

    public UserPointService(UserPointTable userPointTable) {
        this.userPointTable = userPointTable;
    }

    UserPointTable userPointTable;

    PointHistoryTable pointHistoryTable;

    // 1. 포인트 조회
    public UserPoint point(long id, long amount) throws InterruptedException {
        UserPoint userPoint = userPointTable.selectById(id);
        if (userPoint == null) {
            throw new TestException(TestErrorType.POINT_NOT_FOUND);
        }
        return userPointTable.selectById(id);
    }

    // 2. 포인트 충전/이용 내역
    public List<PointHistory> history(long id, long amount) throws InterruptedException {
        List<PointHistory> pointHistory = pointHistoryTable.selectAllByUserId(id);
        if (pointHistory == null) {
            throw new RuntimeException();
        }
        return pointHistoryTable.selectAllByUserId(id);
    }



    // 3. 포인트 충전
    public UserPoint charge(long id, long amount) throws InterruptedException {
        UserPoint userPoint = userPointTable.insertOrUpdate(id, amount);
        if (userPoint == null) {
            throw new TestException(TestErrorType.NOT_ENOUGH_MONEY);
        }
        userPoint.charge(amount, new Date().getTime());
        return userPointTable.insertOrUpdate(id, userPoint.point);
    }

    // 4. 포인트 사용
    public UserPoint use(long id, long amount) throws InterruptedException {
        UserPoint userPoint = userPointTable.insertOrUpdate(id, amount);
        if (userPoint == null) {
            throw new TestException(TestErrorType.POINT_NOT_FOUND);
        }
        userPoint.use(amount, new Date().getTime());
        return userPointTable.insertOrUpdate(id, userPoint.point);
    }
}