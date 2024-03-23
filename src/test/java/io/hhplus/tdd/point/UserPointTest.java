package io.hhplus.tdd.point;

import org.junit.jupiter.api.Test;
import java.util.Date;
class UserPointTest {

    @Test
    public void test() {
        UserPoint userPoint = new UserPoint(1, 100000, 0);
        long updateMillis = new Date().getTime();
        userPoint.charge(0, updateMillis);

        assert userPoint.point == 100000;
        assert userPoint.updateMillis == updateMillis;
    }

    @Test
    public void test2() {
        UserPoint userPoint = new UserPoint(1, 100000, 0);
        long updateMillis = new Date().getTime();
        userPoint.charge(10000, updateMillis);

        assert userPoint.point == 110000;
        assert userPoint.updateMillis == updateMillis;
    }



    @Test
    public void test3() {
        UserPoint userPoint = new UserPoint(1, 100000, 0);
        long updateMillis = new Date().getTime();
        Exception e = null;

        try {
            userPoint.use(100000, updateMillis);
        } catch (Exception exception) {
            e = exception;
        }
        assert e != null;
    }

    @Test
    public void test4() {
        UserPoint userPoint = new UserPoint(1, 100000, 0);
        long updateMillis = new Date().getTime();
        userPoint.use(50000, updateMillis);

        assert userPoint.point == 50000;
        assert userPoint.updateMillis == updateMillis;

    }
}