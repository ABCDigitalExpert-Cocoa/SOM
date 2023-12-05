package com.example.som.model.survey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SurveyResultData {

    private SurveyResponse stressLevel_1 = SurveyResponse.NONE;
    private SurveyResponse stressLevel_2 = SurveyResponse.NONE;
    private SurveyResponse stressLevel_3 = SurveyResponse.NONE;
    private SurveyResponse stressLevel_4 = SurveyResponse.NONE;
    private SurveyResponse stressLevel_5 = SurveyResponse.NONE;
    private SurveyResponse stressLevel_6 = SurveyResponse.NONE;
    private SurveyResponse stressLevel_7 = SurveyResponse.NONE;
    private SurveyResponse stressLevel_8 = SurveyResponse.NONE;
    private SurveyResponse stressLevel_9 = SurveyResponse.NONE;
    private SurveyResponse stressLevel_10 = SurveyResponse.NONE;

    // 생성자, getter 및 setter 메서드 등 필요한 코드 추가
    
    public int getstressLevel_1Score() {
    	return stressLevel_1 != null ? stressLevel_1.getScore() : 0;
    }

    public int getStressLevel_2Score() {
    	return stressLevel_2 != null ? stressLevel_2.getScore() : 0;
    }
    
    public int getStressLevel_3Score() {
    	return stressLevel_3 != null ? stressLevel_3.getScore() : 0;
    }
    
    public int getStressLevel_4Score() {
    	return stressLevel_4 != null ? stressLevel_4.getScore() : 0;
    }
    
    public int getStressLevel_5Score() {
    	return stressLevel_5 != null ? stressLevel_5.getScore() : 0;
    }
    
    public int getStressLevel_6Score() {
    	return stressLevel_6 != null ? stressLevel_6.getScore() : 0;
    }
    
    public int getStressLevel_7Score() {
    	return stressLevel_7 != null ? stressLevel_7.getScore() : 0;
    }
    
    public int getStressLevel_8Score() {
    	return stressLevel_8 != null ? stressLevel_8.getScore() : 0;
    }
    
    public int getStressLevel_9Score() {
    	return stressLevel_9 != null ? stressLevel_9.getScore() : 0;
    }
    
    public int getStressLevel_10Score() {
    	return stressLevel_10 != null ? stressLevel_10.getScore() : 0;
    }

}