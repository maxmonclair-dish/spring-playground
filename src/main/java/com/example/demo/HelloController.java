package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String helloWorld() {
        return "Hello, Earthlings! Who's in charge here?";
    }

    @GetMapping("/math/pi")
    public String pi() { return "3.141592653589793"; }

}
