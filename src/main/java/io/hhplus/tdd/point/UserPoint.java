package io.hhplus.tdd.point;
import io.hhplus.tdd.database.UserPointTable;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserPoint {
    long id;
    public long point;
    long updateMillis;

    public UserPoint(long id, long point, long updateMillis) {
        this.id = id;
        this.point = point;
        this.updateMillis = updateMillis;
    }
    UserPointTable userPointTable;

    public static UserPoint empty(long id) {
        return new UserPoint(id, 0L, System.currentTimeMillis());
    }

//    public void point(Long id, Long point) throws InterruptedException {
//        return userPointTable.selectById(id).point(point);
//    }

    public void charge(long amount, long updateMillis) {
        point += amount;
        this.updateMillis = updateMillis;
    }

    public void use(long amount, long updateMillis) {
        if (point < amount) {
            throw new RuntimeException();
        }
        point -= amount;
        this.updateMillis = updateMillis;
    }
}