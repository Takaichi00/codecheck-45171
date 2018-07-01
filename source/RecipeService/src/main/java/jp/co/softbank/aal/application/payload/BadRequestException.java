package jp.co.softbank.aal.application.payload;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * REST リクエストが不正であることを示す例外クラスです。
 */
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Value
public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 3929668415935569239L;
    private final String message;
    private final String required;
}
