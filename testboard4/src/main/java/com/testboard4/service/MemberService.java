package com.testboard4.service;

import org.springframework.stereotype.Service;

import com.testboard4.dto.MemberDTO;

@Service
public interface MemberService {
	
	public void insertMember(MemberDTO memberDTO);
	public MemberDTO getMemberOne(int num);
	// MemberDTO 객체 타입으로 반환하는 getMemberOne method 
	// int num 을 인자 값으로 받아서 num 에 해당하는 회원 정보 반환 

}
