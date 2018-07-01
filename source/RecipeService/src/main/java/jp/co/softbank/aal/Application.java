package jp.co.softbank.aal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Sprint Boot アプリケーションクラス。
 */
@SpringBootApplication
public class Application {
    
    /**
     * このクラスの main メソッド。
     * 
     * @param args main メソッドの引数。
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
