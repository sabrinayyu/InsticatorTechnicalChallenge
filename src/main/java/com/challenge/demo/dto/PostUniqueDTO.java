package com.challenge.demo.dto;

import com.challenge.demo.entity.Question;
import com.challenge.demo.entity.Site;
import com.challenge.demo.enums.QuestionType;

import java.util.UUID;

/**
 * for request body of /questions/unique
 */

public class PostUniqueDTO {
    private String sitecpUUID;

    private String siteUUID;

    public String getSitecpUUID() {
        return sitecpUUID;
    }

    public void setSitecpUUID(String sitecpUUID) {
        this.sitecpUUID = sitecpUUID;
    }

    public String getSiteUUID() {
        return siteUUID;
    }

    public void setSiteUUID(String siteUUID) {
        this.siteUUID = siteUUID;
    }

    //todo create site and createSitecp here?
//    public static User createUser(final QuestionDTO incomingQuestion, final Site site) {
//        final Question newQ = new Question();
//        newQ.setSite(site);
//        newQ.setQuestion(incomingQuestion.getQuestion());
//        newQ.setType(QuestionType.forValue(incomingQuestion.getType()));
//
//        return newQ;
//    }



}
