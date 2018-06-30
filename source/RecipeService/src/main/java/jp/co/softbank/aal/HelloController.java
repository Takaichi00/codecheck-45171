package jp.co.softbank.aal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recipes")
public class HelloController {
    
    @RequestMapping(method=RequestMethod.GET)
    public String getRecipe() {
        return "Hello World.";
    }
    
}
