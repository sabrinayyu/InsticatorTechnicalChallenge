package com.challenge.demo.dto;

import com.challenge.demo.entity.Question;
import com.challenge.demo.entity.QuestionAnswer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuestionAnswerDTO {

	private Long id;

//	private Long questionId;

	private String answer;

	private boolean isCorrectAnswer;

	private boolean isRowOption;

//	private Date createdAt;
//
//	private Date updatedAt;

	public static QuestionAnswer transform(final QuestionAnswerDTO newQADto, final Question question) {
		final QuestionAnswer newQa = new QuestionAnswer();
		newQa.setAnswer(newQADto.getAnswer());
		newQa.setIsCorrectAnswer(newQADto.getIsCorrectAnswer());
		newQa.setIsRowOption(newQADto.getIsRowOption());
		newQa.setQuestion(question);

		return newQa;
	}

	public static QuestionAnswerDTO build(final QuestionAnswer save) {
		final QuestionAnswerDTO newQaDto = new QuestionAnswerDTO();

		newQaDto.setId(save.getId());
		newQaDto.setAnswer(save.getAnswer());
		newQaDto.setIsCorrectAnswer(save.getIsCorrectAnswer());
		newQaDto.setIsRowOption(save.getIsRowOption());
//		newQaDto.setCreatedAt(save.getCreatedAt());
//		newQaDto.setQuestionId(save.getQuestion().getQuestionId());

		return newQaDto;
	}

	public static List<QuestionAnswerDTO> build(final List<QuestionAnswer> answers) {
		final List<QuestionAnswerDTO> ret = new ArrayList<>();
		for (QuestionAnswer qa : answers) {
			ret.add(build(qa));
		}
		return ret;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

//	public Long getQuestionId() {
//		return questionId;
//	}
//
//	public void setQuestionId(final Long questionId) {
//		this.questionId = questionId;
//	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(final String answer) {
		this.answer = answer;
	}

	public boolean getIsCorrectAnswer() {
		return isCorrectAnswer;
	}

	public void setIsCorrectAnswer(final boolean correctAnswer) {
		isCorrectAnswer = correctAnswer;
	}

	public boolean getIsRowOption() {
		return isRowOption;
	}

	public void setIsRowOption(final boolean isRowOption) {
		this.isRowOption = isRowOption;
	}

/*	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(final Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(final Date updatedAt) {
		this.updatedAt = updatedAt;
	}*/
}
