package com.testboard4.controller;

import jakarta.servlet.http.HttpServletRequest;

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
	
	@GetMapping("/member/memberWriteForm") //사용자가 입력(요청)
 	public String memberWriteForm(
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
				
				// DB에서 가져온 회원 정보가 없을 경우 --> 즉, m1 객체가 null 인 경우.
				if(m1==null) {
					
					System.out.println("--------------------------");
					System.out.println("There is no INFO");
					System.out.println("--------------------------");
					// 1번 방식 : redirect (가장 간단)  -> 메인으로 이동
					//return "redirect:/member/memberWriteForm" ;
					
					
					// 2번 방식 : PrintWriter 사용 
					// import 필요하고 추가 처리 필요(아래 코드와 같은.) 
					// HttpServletResponse response > throws Exception { ... 
					
					// 3번 방식 : 특정 페이지(Error Msg Page)로 데이터 값들을 (Model을 사용해서) 보내서 출력  
					// alert 창을 열어서 경고문 띄우기 :단지 이 때 thymeleaf 문법대로 작성해줘야 하는 부분이 있음 
					model.addAttribute("msg","There is NO INFORMATION. Moving to the Mainpage");
					model.addAttribute("url","/"); // mainpage 로 보냄 
					
					return "/member/messageAlert"; // messageAlert.html 을 만들어야 함 
				}
				
				//잘 되는지 콘솔에 출력
				System.out.println(m1.getName());
				System.out.println(m1.getId());
				System.out.println(m1.getPhone());
				System.out.println("------------------------------");
				
				//Form 페이지로 m1 객체를 전달 : Model 사용 
				model.addAttribute("memberDTO",m1); // modelDTO 라는 이름으로 m1 값이 전달 
				model.addAttribute("formTitle","Modification");
				model.addAttribute("num", num);
				
				
			} else {
				System.out.println("it's null");
				// num = null 이라는 것은 파라미터 값으로 num 값이 넘어온게 없다는 것이므로 "입력" 처리라고 볼 수 있음 
				// 따라서, 여기에다 등록에 대한 처리 코드 작성해야함 
				// MemberWriteForm 에서는 객체를 받아주도록 코드를 짰기 때문에 빈 객체라도 생성해서 받아줘야 함 
				
				// 등록 처리 (신규 회원)
				model.addAttribute("memberDTO", new MemberDTO());
				model.addAttribute("formTitle", "Registration");
			}
		
		 return "/member/memberWriteForm"; // memberwriteForm.html 호출 
	}
	
	// 회원 등록 Ok 
	
	@PostMapping("/member/memberWriteOk")
	public String insertMember(
			MemberDTO m1, 
			Model model ) {
		// form 에서 입력한 값이 여기로 넘어오는데 
		// 그 때, MemberDTO 개체가 자동으로 생성돼서 그 안에 값이 담겨 넘어오게 해야함 
		
		try {
			// 등록 처리
			System.out.println(m1.getName());
			System.out.println(m1.getId());
			System.out.println(m1.getPhone());
			System.out.println("------------------------------");

			memberService.insertMember(m1);
			
			// 등록 안내 메시지 출력
			model.addAttribute( "msg", "회원 등록이 처리되었습니다. 메인 페이지로 이동합니다." );
			model.addAttribute( "url", "/" );
			
			return "/member/messageAlert";  // messageAlert.html 로 이동 
			
		} catch (Exception e) {
			// err
		}

		return "redirect:/";
	
	}
		// 사용자의 정보를 받아서 DB 에 입력 (=등록) 처리를 해 주면 됨 
		
		// 그냥 return 처리를 하는 것과 redirect 리턴의 차이 
		// 1. 별 차이는 없다 ... (^^) 
		// 2. 다만 redirect 의 경우, 다시 한 번 해당 url 로 http 요청을 넣는 형태
	

	
	// 회원 수정OK
	
	@PostMapping("/member/memberUpdateOk")
	public String updateMember(
			MemberDTO m1,
			HttpServletRequest request,
			Model model) {
		// form 에서 입력한 값이 여기로 넘어오는데 
		// 그 때, MemberDTO 개체가 자동으로 생성돼서 그 안에 값이 담겨 넘어오게 해야함 
		
		// 넘어오는 num 값을 받아서 정수형으로 형변환을 해줘야함 
		// getParameter() 반환이 String 이므로 
		String num_ = request.getParameter("num");
		int num = Integer.parseInt(num_);
		
		System.out.println(num);
		
		try { // (사용자가 입력한 정보를) 등록처리 
			// console 에 출력해보기 
			System.out.println(m1.getName());
			System.out.println(m1.getId());
			System.out.println(m1.getPhone());
			System.out.println( "넘어온 번호는 = " + num );
			System.out.println("------------------------------");
			
			memberService.updateMember(m1);
			
			// 안내 메시지 및 URL 정보 전달 --> messageAlert.html
			// 3번 방식 : 특정 페이지로 데이터 값들을(Model 사용) 보내서 출력 
			model.addAttribute( "msg", "회원 정보가 수정되었습니다. 확인 페이지로 이동합니다." );
			model.addAttribute( "url", "/member/memberWriteForm?num=" + num );
			
			
			return "/member/messageAlert";
			
		}
		catch(Exception e) { 
			
		}
		return "redirect:/member/memberWriteForm?num=" + num;

	}
}


