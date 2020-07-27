package com.challenge.demo.repository;

import com.challenge.demo.entity.AnswerHistory;
import com.challenge.demo.entity.QuestionAnswer;
import com.challenge.demo.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AnswerHistoryRepository extends JpaRepository<AnswerHistory, Long> {
    @Query(value = "SELECT ah.* FROM answer_history as ah WHERE ah.user_id = ?1 AND ah.answer_round", nativeQuery = true)
    Optional<List<AnswerHistory>> findByUserIdAndAnswerRound(Long userId, Integer answerRound);

}
