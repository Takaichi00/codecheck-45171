package jp.co.softbank.aal.application;

import javax.servlet.http.HttpServletRequest;
import jp.co.softbank.aal.application.payload.BadRequestException;
import jp.co.softbank.aal.application.payload.ErrorResponse;
import jp.co.softbank.aal.application.payload.InternalServerException;
import jp.co.softbank.aal.application.payload.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * レシピ REST API で、例外をハンドリングして、エラーメッセージを送出するクラスです。
 */
@ControllerAdvice
public class RecipesExceptionHandler {
    
    /**
     * {@link BadRequestException} の例外をハンドリングして、エラーメッセージを送出します。
     * 
     * @param request HTTP リクエスト
     * @param e {@link BadRequestException} の例外
     * @return エラーメッセージ
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> getException(HttpServletRequest request,
                                                      BadRequestException e) {
        return ErrorResponse.createResponse(e);
    }
    
    /**
     * {@link NotFoundException} の例外をハンドリングして、エラーメッセージを送出します。
     * 
     * @param request HTTP リクエスト
     * @param e {@link NotFoundException} の例外
     * @return エラーメッセージ
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> getException(HttpServletRequest request,
                                                      NotFoundException e) {
        return ErrorResponse.createResponse(e);
    }
    
    /**
     * {@link InternalServerException} の例外をハンドリングして、エラーメッセージを送出します。
     * 
     * @param request HTTP リクエスト
     * @param e {@link InternalServerException} の例外
     * @return エラーメッセージ
     */
    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorResponse> getException(HttpServletRequest request,
                                                      InternalServerException e) {
        return ErrorResponse.createResponse(e);
    }
}
