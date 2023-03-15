package com.testboard4.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.testboard4.dto.MemberDTO;

@Service
public interface MemberService {
	
	public void insertMember(MemberDTO memberDTO);
	// MemberDTO 객체 타입으로 반환하는 getMemberOne method 
	// int num 을 인자 값으로 받아서 num 에 해당하는 회원 정보 반환 
	public MemberDTO getMemberOne(int num);
	public void updateMember(MemberDTO memberDTO);
	public List<MemberDTO> getMemberList();
	public int deleteMember(int num);
	
	
}
