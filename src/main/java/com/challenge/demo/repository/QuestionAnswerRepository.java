package com.challenge.demo.repository;

import com.challenge.demo.entity.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {
    @Query(value = "SELECT qa.* FROM question_answer as qa WHERE qa.question_answer_id = ?1", nativeQuery = true)
    Optional<QuestionAnswer> findQAByQuestionAnswerId(Long questionAnswerId);

    @Query(value = "SELECT qa.* FROM question_answer as qa WHERE qa.question_id = ?1", nativeQuery = true)
    Optional<List<QuestionAnswer>> findQAsByQuestionId(Long questionId);
}
