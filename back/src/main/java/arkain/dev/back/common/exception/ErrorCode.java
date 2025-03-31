package arkain.dev.back.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    MISSING_REQUEST_PARAMETER(40000, HttpStatus.BAD_REQUEST, "Missing request parameter"),
    INVALID_PARAMETER_FORMAT(40002, HttpStatus.BAD_REQUEST, "Invalid parameter format"),
    NOT_FOUND_END_POINT(40400, HttpStatus.NOT_FOUND, "Not found end point"),
    INTERNAL_SERVER_ERROR(50000, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");



    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;
}
