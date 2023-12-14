package com.example.som.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.som.model.member.Member;
import com.example.som.model.test.Test;

@Mapper
public interface TestMapper {
	void saveTest(Test test);
	
	Test findTestByMemberId(@Param("member_id")String member_id);
}
