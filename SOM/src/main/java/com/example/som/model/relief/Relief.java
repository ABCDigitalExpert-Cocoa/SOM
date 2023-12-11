package com.example.som.model.relief;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Relief {
	private Long seq_id;
	private String title;
	private String content;
	private String stress_level;
	private String mbti;
	private LocalDateTime create_date;
	private LocalDateTime update_date;
	private ReliefCategory relief_category;
	
}
