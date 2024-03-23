package io.hhplus.tdd.service;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.UserPoint;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserPointService {

    public UserPointService(UserPointTable userPointTable) {
        this.userPointTable = userPointTable;
    }

    UserPointTable userPointTable;

//     1. 포인트 조회
    public UserPoint point(long id, long amount) throws InterruptedException {
        UserPoint userPoint = userPointTable.selectById(id);
        if (userPoint == null) {
            throw new RuntimeException();
        }
        return userPointTable.selectById(id);
    }

    // 2. 포인트 충전/이용 내역


    // 3. 포인트 충전
    public UserPoint charge(long id, long amount) throws InterruptedException {
        UserPoint userPoint = userPointTable.selectById(id);
        if (userPoint == null) {
            throw new RuntimeException();
        }
        userPoint.charge(amount, new Date().getTime());
        return userPointTable.insertOrUpdate(id, userPoint.point);
    }

    // 4. 포인트 사용
    public UserPoint use(long id, long amount) throws InterruptedException {
        UserPoint userPoint = userPointTable.selectById(id);
        if (userPoint == null) {
            throw new RuntimeException();
        }
        userPoint.use(amount, new Date().getTime());
        return userPointTable.insertOrUpdate(id, userPoint.point);
    }
}