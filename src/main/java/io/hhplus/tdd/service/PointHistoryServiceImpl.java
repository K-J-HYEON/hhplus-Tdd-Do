package io.hhplus.tdd.service;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.point.PointHistory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class PointHistoryServiceImpl implements PointHistoryService {
    private final PointHistoryTable pointHistoryTable;

    public PointHistoryServiceImpl(PointHistoryTable pointHistoryTable) {
        this.pointHistoryTable = pointHistoryTable;
    }

//     1. 포인트 충전/이용 내역
    public List<PointHistory> history(Long id) {
        return pointHistoryTable.selectAllByUserId(id);
    }
}
