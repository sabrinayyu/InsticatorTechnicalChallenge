package com.challenge.demo.service;

import com.challenge.demo.dto.WholeQuestionDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface QuestionService {

    WholeQuestionDTO getUniqueWholeQuestion(UUID siteUUID, UUID userUUID);
}
