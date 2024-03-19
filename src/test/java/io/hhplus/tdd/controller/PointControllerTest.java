package io.hhplus.tdd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.dto.request.UserPointReqDto;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.service.PointHistoryService;
import io.hhplus.tdd.service.PointHistoryServiceImpl;
import io.hhplus.tdd.service.UserPointService;
import io.hhplus.tdd.service.UserPointServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

// controller 테스트
/**
 * @WebMvcTest
 * - JPA 기능은 동작하지 않는다.
 * - 여러 스프링 테스트 어노테이션 중, Web(Spring MVC)에만 집중할 수 있는 어노테이션
 * - @Controller, @ControllerAdvice 사용 가능
 * - 단, @Service, @Repository등은 사용할 수 없다.
 * */
@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class PointControllerTest {

    @Mock
    private UserPointService userPointService;
    private PointHistoryService pointHistoryService;

    @InjectMocks
    private PointController pointController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        UserPointTable userPointTable = new UserPointTable();
        PointHistoryTable pointHistoryTable = new PointHistoryTable();
        userPointService = new UserPointServiceImpl(userPointTable);
        pointHistoryService = new PointHistoryServiceImpl(pointHistoryTable);
    }

    @Test
    @DisplayName("사용자 포인토 조회")
    public void getPointTest() throws InterruptedException {

        // given
        Long id = 1L;
        Long amount = 1L;
        UserPointReqDto userPointReqDto = new UserPointReqDto(amount);
        pointController.point(id);

        // when

        // then
    }
}