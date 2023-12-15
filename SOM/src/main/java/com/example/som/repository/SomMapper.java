package com.example.som.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.som.model.diary.Diary;


@Mapper
public interface SomMapper {
	List<Long> getRecentStress(String member_id);
	List<String> getRecentTestDate(String member_id);
	List<Diary> getRecentDiary(String member_id);
}
