package jp.co.softbank.aal.application.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * カスタムのエラーメッセージを表現するクラスです。
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private String message;
    private String required;
    
    /**
     * {@link BadRequestException} の例外から、エラーレスポンスの {@link ResponseEntity}
     * を構築して返します。
     * 
     * @param e {@link BadRequestException} の例外
     * @return エラーレスポンスの {@link ResponseEntity} 
     */
    public static ResponseEntity<ErrorResponse> createResponse(BadRequestException e) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage(),
                                                                   e.getRequired()),
                                                 HttpStatus.OK);
    }
    
    /**
     * {@link NotFoundException} の例外から、エラーレスポンスの {@link ResponseEntity}
     * を構築して返します。
     * 
     * @param e {@link NotFoundException} の例外
     * @return エラーレスポンスの {@link ResponseEntity} 
     */
    public static ResponseEntity<ErrorResponse> createResponse(NotFoundException e) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage(), null),
                                                 HttpStatus.NOT_FOUND);
    }
    
    /**
     * {@link InternalServerException} の例外から、エラーレスポンスの {@link ResponseEntity}
     * を構築して返します。
     * 
     * @param e {@link InternalServerException} の例外
     * @return エラーレスポンスの {@link ResponseEntity} 
     */
    public static ResponseEntity<ErrorResponse> createResponse(InternalServerException e) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage(), null),
                                                 HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
