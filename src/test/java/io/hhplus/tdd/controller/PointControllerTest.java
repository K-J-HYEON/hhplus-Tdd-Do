package io.hhplus.tdd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.dto.request.UserPointReqDto;
import io.hhplus.tdd.point.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserPointTable userPointTable;

    @Autowired
    private PointHistoryTable pointHistoryTable;

    @BeforeEach
    void setUp() throws InterruptedException {
        Long userId = 1L;
        Long amount = 1L;
        TransactionType transactionType = TransactionType.CHARGE;
        Long updateMillis = System.currentTimeMillis();

        userPointTable.insertOrUpdate(userId, amount);
        pointHistoryTable.insert(userId, amount, transactionType, updateMillis);
    }

    @Test
    @DisplayName("사용자 포인트 조회")
    void getUserPointTest() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/point/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.point").value(100L));
    }

    @Test
    @DisplayName("사용자 포인트 충전/이용 내역 조회")
    void getPointHistoryTest() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/point/{id}/histories", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].type").value(TransactionType.CHARGE.toString()))
                .andExpect(jsonPath("$[0].amount").value(1000L));
    }


    @Test
    @DisplayName("사용자 포인트 충전")
    void  increaseUserPointTest() throws Exception {
        UserPointReqDto userPointReqDto = UserPointReqDto.of(1L);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/point/{id}/charge", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userPointReqDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.point").value(1000L));
    }


    @Test
    @DisplayName("사용자 포인트 사용")
    void decreaseUserPointTest() throws Exception {
        UserPointReqDto userPointReqDto = UserPointReqDto.of(1000L);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .patch("/point/{id}/use", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userPointReqDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.point").value(0L));
    }
}