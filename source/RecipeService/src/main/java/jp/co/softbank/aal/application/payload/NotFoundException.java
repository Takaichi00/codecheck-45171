package jp.co.softbank.aal.application.payload;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * REST リクエストで指定されたリソースが存在しないことを示す例外クラスです。
 */
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Value
public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = -306736661495981369L;
    private final String message;
}
