package com.delivery.app.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// ctrl + shift + O
// cmd + shift + O

@Controller
public class TesteController {

	@GetMapping("/hello")
	@ResponseBody
	public String hello() {
		
		return "Hello world!!!";
		
	}
	
}
