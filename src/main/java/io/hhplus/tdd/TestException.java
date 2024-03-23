package io.hhplus.tdd;

import lombok.Getter;

@Getter
public class TestException extends RuntimeException {
    TestErrorType testErrorType;
    public TestException(TestErrorType testErrorType) {
        this.testErrorType = testErrorType;
    }
}
