package io.hhplus.tdd.controller;

import io.hhplus.tdd.dto.request.UserPointReqDto;
import io.hhplus.tdd.dto.response.UserPointRespDto;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.service.PointHistoryServiceImpl;
import io.hhplus.tdd.service.UserPointServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/point")
@RestController
public class PointController {

    private final UserPointServiceImpl userPointServiceImpl;
    private final PointHistoryServiceImpl pointHistoryServiceImpl;

    public PointController(UserPointServiceImpl userPointServiceImpl, PointHistoryServiceImpl pointHistoryServiceImpl) {
        this.userPointServiceImpl = userPointServiceImpl;
        this.pointHistoryServiceImpl = pointHistoryServiceImpl;
    }

    /**
     * TODO - 특정 유저의 포인트를 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{id}")
    public ResponseEntity<List<UserPointRespDto>> point(@PathVariable(name = "id") Long id) throws InterruptedException {
        return ResponseEntity.ok(userPointServiceImpl.getUserPoint(id));
    }

    /**
     * TODO - 특정 유저의 포인트 충전/이용 내역을 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{id}/histories")
    public ResponseEntity<List<PointHistory>> history(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(pointHistoryServiceImpl.getUserPointHistory(id));
    }

    /**
     * TODO - 특정 유저의 포인트를 충전하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/charge")
    public ResponseEntity<List<UserPointRespDto>> charge(@PathVariable(name = "id") Long id, @RequestBody @Valid UserPointReqDto request) {
        return ResponseEntity.ok(userPointServiceImpl.increaseUserPoint(id, request.point()));
    }

    /**
     * TODO - 특정 유저의 포인트를 사용하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/use")
    public ResponseEntity<List<UserPointRespDto>> use(@PathVariable(name = "id") Long id, @RequestBody @Valid UserPointReqDto request) {
        return ResponseEntity.ok(userPointServiceImpl.decreaseUserPoint(id, request.point()));
    }
}
