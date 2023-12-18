package com.example.som.model.test;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Test {
	private Long test_id;
	private String member_id;
	private String test_result;
	private LocalDateTime test_date;
	private Long stress_level;
	
	public void setStressLevel() {
		int result = Integer.parseInt(this.test_result);
		if(result <= 13) {
			this.stress_level = 1L;
		} else if(result > 13 && result <= 16) {
			this.stress_level = 2L;
		} else if(result > 16 && result <= 18) {
			this.stress_level = 3L;
		} else if(result > 18) {
			this.stress_level = 4L;
		}
	}

	
}
