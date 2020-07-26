package com.challenge.demo.dto;

import com.challenge.demo.entity.Question;
import com.challenge.demo.entity.QuestionAnswer;
import com.challenge.demo.entity.Site;
import com.challenge.demo.enums.QuestionType;

import java.util.ArrayList;
import java.util.List;

public class WholeQuestionDTO {

    private QuestionDTO questionDTO;

    private List<QuestionAnswerDTO> options;

    public WholeQuestionDTO() {
        this.questionDTO = new QuestionDTO();
        this.options = new ArrayList<QuestionAnswerDTO>();
    }

    public static WholeQuestionDTO build(Question question, List<QuestionAnswer> questionAnswers) {
        final WholeQuestionDTO obj = new WholeQuestionDTO();
        obj.setQuestionDTO(QuestionDTO.build(question));
        obj.setOptions(QuestionAnswerDTO.build(questionAnswers));

        return obj;
    }

    public QuestionDTO getQuestionDTO() { return questionDTO; }

    public void setQuestionDTO(QuestionDTO questionDTO) { this.questionDTO = questionDTO; }

    public List<QuestionAnswerDTO> getOptions() { return options; }

    public void setOptions(List<QuestionAnswerDTO> options) { this.options = options; }

}
