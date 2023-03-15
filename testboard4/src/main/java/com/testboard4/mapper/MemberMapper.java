package com.testboard4.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.testboard4.dto.MemberDTO;

@Mapper
public interface MemberMapper {
	

	public void insertMember(MemberDTO memberDTO);
	
	public MemberDTO selectMemberOne(int num);
	
	public void updateMember(MemberDTO memberDTO);
	
	public List<MemberDTO> selectMemberAll();
	

}
