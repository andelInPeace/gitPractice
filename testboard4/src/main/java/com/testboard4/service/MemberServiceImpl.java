package com.testboard4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testboard4.dto.MemberDTO;
import com.testboard4.mapper.MemberMapper;
 
@Service
public class MemberServiceImpl implements MemberService{
//	service.MemerService 구현 
	
	@Autowired
	private MemberMapper memberMapper;
	
	//Insert (등록) 
	
	@Override
	public void insertMember(MemberDTO memberDTO) {
		
		memberMapper.insertMember(memberDTO);
		
	}
	
	// Select Member One (수정) 
	
	@Override
	public MemberDTO getMemberOne(int num) {
		
		return memberMapper.selectMemberOne(num);
	}

}
