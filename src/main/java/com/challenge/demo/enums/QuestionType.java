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
     *
     * each question could at most have [correctAnswerMin, correctAnswerMax] correct answer
     *
     */

    TRIVIA (1, 2, 4),
    POLL (0, 2,4),
    CHECKBOX (0, 1, 10),
    MATRIX(0, 1, 999999);

    private static Map<String, QuestionType> questionTypeMap = new HashMap<String, QuestionType>(3);

    static {
        questionTypeMap.put("trivia", TRIVIA);
        questionTypeMap.put("poll", POLL);
        questionTypeMap.put("checkbox", CHECKBOX);
        questionTypeMap.put("matrix", MATRIX);
    }

    @JsonCreator
    public static QuestionType forValue (String value) throws NoSuchFieldException {
        value = StringUtils.lowerCase(value);
        if (!questionTypeMap.containsKey(value))
            throw new NoSuchFieldException("wrong question type!");

        return questionTypeMap.get(value);
    }

    @JsonValue
    public String toValue() {
        for (Map.Entry<String, QuestionType> entry : questionTypeMap.entrySet()) {
            if (entry.getValue() == this)
                return StringUtils.upperCase(entry.getKey());
        }

        return null; // or fail
    }

    private Integer correctAnswer;

    private Integer AnswerMin;

    private Integer AnswerMax;
    QuestionType(Integer correctAnswer, Integer answerMin, Integer answerMax) {
        this.correctAnswer = correctAnswer;
        AnswerMin = answerMin;
        AnswerMax = answerMax;
    }

    public Integer getCorrectAnswer() {
        return correctAnswer;
    }

    public Integer getAnswerMin() {
        return AnswerMin;
    }

    public Integer getAnswerMax() {
        return AnswerMax;
    }


}
