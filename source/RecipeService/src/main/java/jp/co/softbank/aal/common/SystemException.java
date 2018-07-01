package jp.co.softbank.aal.common;

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * データベース・アクセス・エラーやソケット接続エラーなどのシステム例外を示す例外クラスです。
 */
@EqualsAndHashCode(callSuper = false)
@Value
public class SystemException extends RuntimeException {
    
    private static final long serialVersionUID = 1999201690598153264L;
    
    /**
     * 指定されたパラメータを使用して {@link SystemException} のインスタンスを構築します。
     * 
     * @param message 詳細メッセージ
     */
    public SystemException(String message) {
        super(message);
    }
    
    /**
     * 指定されたパラメータを使用して {@link SystemException} のインスタンスを構築します。
     * 
     * @param message 詳細メッセージ
     * @param cause 原因
     */
    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
