package com.challenge.demo.service;

import com.challenge.demo.dto.AnswerColRowDTO;
import com.challenge.demo.dto.WholeQuestionDTO;
import com.challenge.demo.entity.AnswerHistory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface QuestionService {

    WholeQuestionDTO getUniqueWholeQuestion(UUID siteUUID, UUID userUUID);

    List<AnswerHistory> createAnswerHistory(UUID userUUID, Long questionId, List<AnswerColRowDTO> userAnswers);
}
