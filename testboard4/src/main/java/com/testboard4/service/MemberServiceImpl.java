package com.testboard4.service;

import java.util.List;

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
	
	// Update
	
	@Override
	public void updateMember(MemberDTO memberDTO) {
		
		memberMapper.updateMember(memberDTO);
		
	}
	
	// Select Member All 
	@Override
	public List<MemberDTO> getMemberList(){
		
		return memberMapper.selectMemberAll();
	}
	
	// Delete Member One
	public int deleteMember(int num) {
		return memberMapper.deleteMemberOne(num);
	}

}
