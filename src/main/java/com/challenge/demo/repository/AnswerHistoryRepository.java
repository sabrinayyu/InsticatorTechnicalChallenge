package com.challenge.demo.repository;

import com.challenge.demo.entity.AnswerHistory;
import com.challenge.demo.entity.QuestionAnswer;
import com.challenge.demo.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerHistoryRepository extends JpaRepository<AnswerHistory, Long> {

}
