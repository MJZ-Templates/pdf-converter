package arkain.dev.back.common.dto;

import arkain.dev.back.common.exception.ErrorCode;

public record ExceptionDto(
        Integer code,
        String message
) {

    public static ExceptionDto of(ErrorCode errorCode) {
        return new ExceptionDto(errorCode.getCode(), errorCode.getMessage());
    }
}
