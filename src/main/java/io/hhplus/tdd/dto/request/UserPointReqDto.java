package io.hhplus.tdd.dto.request;


import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public record UserPointReqDto(
        @NotNull
        Long point
) {
    public UserPointReqDto {
        Objects.requireNonNull(point, "Point is not null");
    }

}
