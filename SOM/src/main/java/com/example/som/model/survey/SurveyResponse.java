package com.example.som.model.survey;

public enum SurveyResponse {
    NONE(0),
    ALMOST_NONE(1),
    SOMETIMES(2),
    OFTEN(3),
    VERY_OFTEN(4);

    private final int score;

    SurveyResponse(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
