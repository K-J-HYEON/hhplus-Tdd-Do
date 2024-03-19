package io.hhplus.tdd.dto.response;

import io.hhplus.tdd.point.UserPoint;

public record UserPointRespDto(
        Long id,
        Long point,
        Long updateMillis
) {

    public static UserPointRespDto of(UserPoint userPoint) {
        return new UserPointRespDto(
                userPoint.id(),
                userPoint.point(),
                userPoint.updateMillis()
        );
    }

}