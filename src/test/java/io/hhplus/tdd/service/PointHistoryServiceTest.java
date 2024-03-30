package io.hhplus.tdd.service;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.dto.request.UserPointReqDto;
import io.hhplus.tdd.point.PointHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class PointHistoryServiceTest {

    @InjectMocks
    PointHistoryServiceImpl sut;

    @Mock
    PointHistoryTable pointHistoryTable;

    @BeforeEach
    void setUp() {
        PointHistoryTable pointHistoryTable = new PointHistoryTable();
        sut = new PointHistoryServiceImpl(pointHistoryTable);
    }

    @Test
    @DisplayName("사용자 포인트 충전/이용 내역 조회")
    void pointHistoryTest() {

        Long userId = 1L;
        Long amount = 1L;
        UserPointReqDto userPointReqDto = new UserPointReqDto(amount);
//        sut.charge(userId, userPointReqDto.point());

        List<PointHistory> userPointRespDto = sut.history(userId);
        assertThat(userPointRespDto).isNotEmpty();
    }
}