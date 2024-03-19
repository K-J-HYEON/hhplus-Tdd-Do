package io.hhplus.tdd.dto.request;


import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public record UserPointReqDto(
        Long point
) {
    public UserPointReqDto {
        Objects.requireNonNull(point, "Point is not null");
    }

    public static UserPointReqDto of(Long amount) {
        return new UserPointReqDto(amount);
    }

}
