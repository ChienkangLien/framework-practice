package org.tutorial.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorsController {

	@GetMapping("/hello")
	public String index(HttpServletResponse response, HttpServletRequest request) {
		System.out.println("in hello");
		System.out.println(request.getHeader("custom-header"));
		return "Hello World";
	}

	@PutMapping("/hello2")
	public String index2(HttpServletResponse response, HttpServletRequest request) {
		System.out.println("in hello2");
		System.out.println(request.getHeader("custom-header"));
		return "Hello World";
	}
}
