package com.testboard4;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.testboard4.dto.MemberDTO;
import com.testboard4.mapper.MemberMapper;

@SpringBootTest
public class MapperTests {

	@Autowired
	private MemberMapper memberMapper;

	@Test
	public void testInsert() {

		MemberDTO m1 = new MemberDTO();

		m1.setName("유애.");
		m1.setId("The Moon");
		m1.setPhone("000-111-2222");

		System.out.println(m1);
		memberMapper.insertMember(m1);

		System.out.println("-------------------------------------------------");
		System.out.println("레코드가 추가되었습니다.");
		System.out.println("-------------------------------------------------");
	}

}
