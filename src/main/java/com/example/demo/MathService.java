package com.example.demo;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MathService {

    @GetMapping("/math/pi")
    public String pi() { return "3.141592653589793"; }

    @GetMapping("/math/calculate/")
    public String calculate(String operation, int x, int y) {
        int result;
        String op = "bad operator";
        switch (operation) {
            case "add":
                result = x + y;
                op = " + ";
                break;
            case "subtract":
                result = x - y;
                op = " - ";
                break;
            case "multiply":
                result = x * y;
                op = " x ";
                break;
            case "divide":
                result = x / y;
                op = " / ";
                break;
            default:
                return op;
        }
        return x + op + y + " = " + result;
    }

    @PostMapping("/math/sum/")
    public String sum(@RequestParam MultiValueMap<String, String> m) {
        Integer result = 0;
        String exp = "";

        for (List<String> values : m.values()) {
            Integer listLen = values.size();
            Integer listCount = 0;
            for (String i : values) {
                result = result + Integer.parseInt(i);

                if (listCount < listLen - 1) {
                    exp = exp + i + " + ";
                } else {
                    exp = exp + i;
                }

                listCount++;
            }
        }
        return exp + " = " + result.toString();
    }

}
