package com.testboard4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.testboard4.dto.MemberDTO;
import com.testboard4.service.MemberService;

@Controller
public class MemberController {
	
	// DI
	
	
	@Autowired
	private MemberService memberService;
	
	// 회원 등록 Form 페이지 + 회원 수정 form 페이지 
	
	@GetMapping("/member/memberWriteForm") //사용자가 입력 
	public String memberwriteForm(
			@RequestParam(value="num", required=false) Integer num,
			Model model) {
			// required 옵션은 default 가 true. 
			// 받지 않아도 되는 parameter 값이면 false 로 지정 해 줘야 함 
			// 기본값 true 로 사용하는 경우 --> 보통 줄여서 @RequestParam("num")
			// int는 null 값을 가질 수 없어서 Integer 값으로 넣어줘야 함 
		
			// 넘어온 값이 null 인지 체크 
			// num 이 null 인지 비교할 때 주의사항 
			// primitive type(원시 타입)인 int는 null 을 사용할 수 없음. null 이 필요한 경우는 Integer 사용. 또는 0을 사용. 
			// if(num !=0)  { ... }
			if(num!=null) {
				System.out.println("num is [" + num +"]");
				// parameter 값으로 null 값이 넘어옴 --> '수정'요청 
				// 따라서, 여기에는 수정에 대한 처리 코드 작성. 
				
				// what should be done is 
				// 우선 넘어온 num 에 대한 회원 정보를 DB 에서 가져와서 -- > 해당 정보를 form 페이지로 전달 (form 에서 text box에 나오게 하기 - Model 사용.)
				// Service 단으로 요청 
				MemberDTO m1 = memberService.getMemberOne(num);
				// 요청이 들어온 num 에 해당하는 회원 정보 요청 
				
				//잘 되는지 콘솔에 출력
				System.out.println(m1.getName());
				System.out.println(m1.getId());
				System.out.println(m1.getPhone());
				
				//Form 페이지로 m1 객체를 전달 : Model 사용 
				model.addAttribute("memberDTO",m1); // modelDTO 라는 이름으로 m1 값이 전달 
				model.addAttribute("formTitle","Modification");
				
				
			} else {
				System.out.println("it's null");
			}
		
		 return "/member/memberWriteForm"; // memberwriteForm.html 호출 
	}
	
	// 회원 등록 Ok 
	
	@PostMapping("/member/memberWriteOk")
	public String insertMember(MemberDTO m1) {
		// form 에서 입력한 값이 여기로 넘어오는데 
		// 그 때, MemberDTO 개체가 자동으로 생성돼서 그 안에 값이 담겨 넘어오게 해야함 
		
		try { // (사용자가 입력한 정보를) 등록처리 
			// console 에 출력해보기 
			System.out.println(m1.getName());
			System.out.println(m1.getId());
			System.out.println(m1.getPhone());
			
			memberService.insertMember(m1);
			// memberService 라는 객체를 사용하기 위해서는 '주입'을 해줘야 함 
			// Service 단에서 호출함 ... !! 
			
		}
		catch(Exception e) {
			
		}
		
		// 사용자의 정보를 받아서 DB 에 입력 (=등록) 처리를 해 주면 됨 
		return "redirect:/";
		// 그냥 return 처리를 하는 것과 redirect 리턴의 차이 
		// 1. 별 차이는 없다 ... (^^) 
		// 2. 다만 redirect 의 경우, 다시 한 번 해당 url 로 http 요청을 넣는 형태 
	}

}
