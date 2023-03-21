package com.testboard4.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {
	
	@GetMapping("/fragments")
	public String indexFragments() {
		
		return "/tpl/tpl_fragments_main"; //tpl_fragments.html 리턴 
	}
	
	
	@GetMapping("/main")
	public String indexMain() {
		
		return "/tpl/tpl_main"; //tpl_main.html 리턴 
	}
	
	@GetMapping("/sub")
	public String indexSub() {
		
		return "/tpl/tpl_sub"; //tpl_sub.html 리턴 
	}
	
	@GetMapping("/fragments_nav")
	public String indexBsTopmenu() {
		
		return "/tpl/tpl_fragments_nav";
	}
	
	@GetMapping("/mainpage")
	public String indexMainPage() {
		
		return "/tpl2/mainpage"; // mainpage.html
	}
	

}
