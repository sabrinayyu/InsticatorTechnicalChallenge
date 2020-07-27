package com.challenge.demo.dto;

import java.util.List;

/**
 * for the request body of /
 */
public class PostUserAnswerDTO {
    private String userUUID;

    private Long questionId;

    private List<AnswerColRowDTO> userAnswers;

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public List<AnswerColRowDTO> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(List<AnswerColRowDTO> userAnswers) {
        this.userAnswers = userAnswers;
    }
}
