package com.challenge.demo.dto;

import com.challenge.demo.entity.Question;
import com.challenge.demo.entity.Site;
import com.challenge.demo.enums.QuestionType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuestionDTO {

	private Long questionId;

	private Long siteId;

	private String type;

	private String question;

//	private Date createdAt;
//
//	private Date updatedAt;

	public static QuestionDTO build(Question question) {
		final QuestionDTO obj = new QuestionDTO();
		obj.setSiteId(question.getSite().getSiteId());
		obj.setQuestionId(question.getQuestionId());
		obj.setQuestion(question.getQuestion());
		obj.setType(question.getType().toValue());
//		obj.setUpdatedAt(question.getUpdatedAt());
//		obj.setCreatedAt(question.getCreatedAt());

		return obj;
	}

	public static List<QuestionDTO> build(List<Question> questions) {
		final List<QuestionDTO> ret = new ArrayList<>();

		for (Question question : questions) {
			ret.add(build(question));
		}

		return ret;
	}

	public static Question createQuestion(final QuestionDTO incomingQuestion, final Site site) throws NoSuchFieldException {
		final Question newQ = new Question();
		newQ.setSite(site);
		newQ.setQuestion(incomingQuestion.getQuestion());
		newQ.setType(QuestionType.forValue(incomingQuestion.getType()));

		return newQ;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(final Long siteId) {
		this.siteId = siteId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(final String question) {
		this.question = question;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) { this.type = type; }

//	public Date getCreatedAt() {
//		return createdAt;
//	}
//
//	public void setCreatedAt(final Date createdAt) {
//		this.createdAt = createdAt;
//	}
//
//	public Date getUpdatedAt() {
//		return updatedAt;
//	}
//
//	public void setUpdatedAt(final Date updatedAt) {
//		this.updatedAt = updatedAt;
//	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(final Long questionId) {
		this.questionId = questionId;
	}
}
