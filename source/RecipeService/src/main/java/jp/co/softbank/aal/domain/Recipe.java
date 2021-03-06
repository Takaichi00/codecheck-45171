package jp.co.softbank.aal.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 単一のレシピを表現するドメインクラスです。
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Recipe {
    private Integer id;
    private String title;
    private String makingTime;
    private String serves;
    private String ingredients;
    private Integer cost;
}
