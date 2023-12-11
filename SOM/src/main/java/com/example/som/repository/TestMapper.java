package com.example.som.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.som.model.test.Test;

@Mapper
public interface TestMapper {
	void saveTest(Test test);
}
