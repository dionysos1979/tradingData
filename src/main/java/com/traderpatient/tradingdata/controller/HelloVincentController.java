package com.traderpatient.tradingdata.controller;

import java.text.DateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloVincentController {

    @GetMapping("/helloVincent")
    public String helloVincent(Model model) {
    	System.out.println("Requête = [http://localhost:8080/serving-web-content-complete/helloVincent]");
    	Date date = new Date();
    	DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(
    	        DateFormat.MEDIUM,
    	        DateFormat.MEDIUM);   	
    	String name = new String("helloVincent ! Il est : " + mediumDateFormat.format(date));
		model.addAttribute("name", name);
    	System.out.println("Requête = [http://localhost:8080/serving-web-content-complete/greeting?name=Vincent]");
		return "helloVincent";
    }
}
