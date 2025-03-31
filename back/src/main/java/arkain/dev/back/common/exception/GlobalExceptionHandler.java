package arkain.dev.back.common.exception;

import arkain.dev.back.common.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            value = {NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseDto<?> handleNoPageFoundException(Exception e) {
        log.error(
                "handleNoPageFoundException() in GlobalExceptionHandler throw NoHandlerFoundException : {}",
                e.getMessage());
        return ResponseDto.fail(new CommonException(ErrorCode.NOT_FOUND_END_POINT));
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseDto<?> handleArgumentNotValidException(MethodArgumentTypeMismatchException e) {
        log.error(
                "handleArgumentNotValidException() in GlobalExceptionHandler throw MethodArgumentTypeMismatchException : {}",
                e.getMessage());
        return ResponseDto.fail(e);
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseDto<?> handleArgumentNotValidException(MissingServletRequestParameterException e) {
        log.error(
                "handleArgumentNotValidException() in GlobalExceptionHandler throw MethodArgumentNotValidException : {}",
                e.getMessage());
        return ResponseDto.fail(e);
    }
}
