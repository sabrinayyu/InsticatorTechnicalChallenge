package com.challenge.demo.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public enum QuestionType {
    /**
     * question type
     * correctAnswerMin:
     *
     */

    TRIVIA (1, 1),
    POLL (2, 4),
    CHECKBOX (0, 0),
    MATRIX(0, 0);

    private static Map<String, QuestionType> questionTypeMap = new HashMap<String, QuestionType>(3);

    static {
        questionTypeMap.put("trivia", TRIVIA);
        questionTypeMap.put("poll", POLL);
        questionTypeMap.put("checkbox", CHECKBOX);
        questionTypeMap.put("matrix", MATRIX);
    }

    @JsonCreator
    public static QuestionType forValue (String value) throws NoSuchFieldException {

        if (!questionTypeMap.containsKey(value))
            throw new NoSuchFieldException("wrong question type!");

        return questionTypeMap.get(StringUtils.lowerCase(value));
    }

    @JsonValue
    public String toValue() {
        for (Map.Entry<String, QuestionType> entry : questionTypeMap.entrySet()) {
            if (entry.getValue() == this)
                return StringUtils.upperCase(entry.getKey());
        }

        return null; // or fail
    }

    private int correctAnswerMin;
    private int correctAnswerMax;

    private QuestionType(int correctAnswerMin, int correctAnswerMax) {
        this.correctAnswerMin = correctAnswerMin;
        this.correctAnswerMax = correctAnswerMax;
    }

    public int getCorrectAnswerMax() {
        return correctAnswerMax;
    }

    public int getCorrectAnswerMin() {
        return correctAnswerMin;
    }

}
