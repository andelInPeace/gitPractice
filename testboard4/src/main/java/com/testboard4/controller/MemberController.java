package com.testboard4.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

import com.testboard4.dto.MemberDTO;
import com.testboard4.service.MemberService;


@Controller
public class MemberController {
	
	// DI
	
	
	@Autowired
	private MemberService memberService;
	
	
	// 회원 등록 Form 페이지 + 회원 수정 form 페이지 
	
	@GetMapping("/member/m2emberWriteFormNew") //사용자가 입력(요청)
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
		
		 return "/member/m2emberWriteFormNew"; // memberwriteFormNew.html 호출 
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
			model.addAttribute("msg", "회원 등록이 처리되었습니다. 메인 페이지로 이동합니다.");
			model.addAttribute("url", "/");
			
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
			System.out.println("------------------------------");
			System.out.println(m1.getName());
			System.out.println(m1.getId());
			System.out.println(m1.getPhone());
			System.out.println( "넘어온 번호는 = " + num );
			System.out.println("------------------------------");
			
			memberService.updateMember(m1);
			
			// 안내 메시지 및 URL 정보 전달 --> messageAlert.html
			// 3번 방식 : 특정 페이지로 데이터 값들을(Model 사용) 보내서 출력 
			model.addAttribute( "msg", "회원 정보가 수정되었습니다. 확인 페이지로 이동합니다." );
			model.addAttribute( "url", "/member/m2emberWriteFormNew?num=" + num );
			
			
			return "/member/messageAlert";
			
		}
		catch(Exception e) { 
			
		}
		return "redirect:/member/m2emberWriteFormNew?num=" + num;

	}
	
	// 회원 리스트 
	
	@GetMapping("/member/memberList")
	public String memberList(Model model) {
		//가져온 데이터를 Model 에 담아서 Viewpage(html) 로 전달 
		
		List<MemberDTO> memberList = memberService.getMemberList();
		
		System.out.println(memberList.get(1).toString()); // num# 3(index=4) 인 데이터 출력 
		// toString() 메서드는 기본적으로 오버라이딩 하지 않으면 객체의 해시코드가 출력 
		// 따라서, 객체의 정보를 출력하고자 한다면 toString() 메서드를 MemberDTO에서 오버라이딩 하여 객체의 정보를 문자열 형태로 출력. 
 		// 1. 이름, Id, phone ~
		// otherwise, com.testboard4.dto.memberDTO@~ 가 출력됨 
		
		
		// 객체 리스트 전달 : 모델에 담아서 리스트 페이지로 전달 
		model.addAttribute("memberList", memberList);
		
		return "/member/memberList"; //memberList.html 페이지로 리턴 
	}
	
	// 회원 삭제Ok 
	// 1. Controller 삭제 구현 (삭제 요청에 대한 매핑 처리, num 변수 처리, 응답 메시지 처리 및 이동 url 전달 처리 등등)
	// 2. 삭제 시 num값이 null 인지 아닌지 체크 (null 이면 redirect)
	// 3. 여러 에러 상황을 대비하여 try ... catch 구문 사용
	// 4. 삭제 처리 후 반환값을 리턴 받아서 --> 게시글 삭제 성공 시 전달할 메시지와 실패 시의 메시지를 각각 전달할 수 있도록 처리 
	// 5. 삭제 처리 후 반환값 ?? --> row 의 갯수  
	
	@GetMapping("/member/memberDeleteOk")
	public String memberDeleteOk(@RequestParam(value="num", required=false) Integer num, Model model) {
		// null 인 경우 (삭제 할 거 없는 경우 --> 안 하면 됨) 
		if(num==null) {
			System.out.println("nul 입니다.");
			return "redirect:/member/memberList";
		}
		System.out.println("해당 num : " + num);
		
		// try ... catch ~
		// 여기가 null 이 아닌 경우라 삭제 해야하는 경우 
		try {
			//삭제에 대한 DB 처리 
			// 삭제 처리 후 --> 반환 값 리턴 
			int isOk= memberService.deleteMember(num);
			System.out.println("isOk = " + isOk);
			
			// 멤버 삭제 실패 시 처리 구현(메시지 등을 전달)
			if(isOk!=1) {
				System.out.println("삭제 실패"); 
				// return "redirect:/member/memberList"; // 모델로 전달해서 메시지 출력 후 이동 시켜도 됨 
				// 삭제 실패시 --> 안내 메시지 및 이동 url 정보 전달 
				model.addAttribute("msg", "삭제 실패. 멤버 리스트로 이동합니다.");
				model.addAttribute("url", "/member/memberList");
			} 
			else {
				// 삭제 성공 시 (isOk=1)
				System.out.println("삭제 성공 = " + isOk);
				
				// 삭제 성공 시 --> 안내 메시지 및 url 정보를 전달 --> messageAlert.html
				model.addAttribute("msg", "삭제 상공. 멤버 리스트로 이동합니다.");
				model.addAttribute("url", "/member/memberList");
			}
		}
		catch(DataAccessException e) {
			// DB 처리시 문제가 있나 ? 
		
			
		}
		catch(Exception e) {
			// 시스템에 문제가 있나 ? 
			
		}
		return "/member/messageAlert"; //messageAlert.html
	}
}


