package jp.co.softbank.aal.application.payload;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * REST エンドポイントでシステムエラーが発生したことを示す例外クラスです。
 */
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Value
public class InternalServerException extends RuntimeException {
    private static final long serialVersionUID = 6427609976718505028L;
    private final String message;
}
