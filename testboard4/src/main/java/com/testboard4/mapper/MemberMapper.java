package com.testboard4.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.testboard4.dto.MemberDTO;

@Mapper
public interface MemberMapper {

	public void insertMember(MemberDTO memberDTO);


}
