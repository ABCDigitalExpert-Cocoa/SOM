package com.example.som.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.som.model.diary.Diary;

@Mapper
public interface DiaryMapper {
	void saveDiary(Diary diary);
	
	List<Diary> findAllDiary(String member_id);
}
