package com.quizmarkt.base.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author anercan
 */

@Data
public class ApiResponse <T>{
    private T data;
    private Status status = new Status();

    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(Status status) {
        this.status = status;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Status {
        private static final int SUCCESS = 0;
        private static final int FAIL_WITH_MESSAGE = -1;
        private static final int FAIL_WITHOUT_MESSAGE = -2;
        private static final int UNAUTHORIZED_PREMIUM_OPERATION = -3;

        private int code = SUCCESS;
        private String message;

        public static Status fail(String message) {
            return new Status(FAIL_WITH_MESSAGE, message);
        }

        public static Status fail() {
            return new Status(FAIL_WITHOUT_MESSAGE, null);
        }

        public static Status notAuthorizedPremiumOperation() {
            return new Status(UNAUTHORIZED_PREMIUM_OPERATION, null);
        }
    }
}
