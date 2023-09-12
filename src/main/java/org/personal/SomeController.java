package org.personal;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SomeController {
    @GetMapping("/test")
    public String test(){
        return "OK but spring";
    }
}
