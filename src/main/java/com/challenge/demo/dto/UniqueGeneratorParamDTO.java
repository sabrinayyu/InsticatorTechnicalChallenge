package com.challenge.demo.dto;

import com.challenge.demo.entity.Question;
import com.challenge.demo.entity.Site;
import com.challenge.demo.enums.QuestionType;

import java.util.UUID;

public class UniqueGeneratorParamDTO {
    private String userUUID;

    private String siteUUID;

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public String getSiteUUID() {
        return siteUUID;
    }

    public void setSiteUUID(String siteUUID) {
        this.siteUUID = siteUUID;
    }

//    public static User createUser(final QuestionDTO incomingQuestion, final Site site) {
//        final Question newQ = new Question();
//        newQ.setSite(site);
//        newQ.setQuestion(incomingQuestion.getQuestion());
//        newQ.setType(QuestionType.forValue(incomingQuestion.getType()));
//
//        return newQ;
//    }



}