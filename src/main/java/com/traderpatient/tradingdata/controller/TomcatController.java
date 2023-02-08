package com.traderpatient.tradingdata.controller;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TomcatController {

    @GetMapping("/hello")
    public Collection<String> sayHello() {
    	System.out.println("Requête = [http://localhost:8080/serving-web-content-complete/hello]");
        return IntStream.range(0, 10)
          .mapToObj(i -> "Hello Vincent number " + i)
          .collect(Collectors.toList());
    }
    
}