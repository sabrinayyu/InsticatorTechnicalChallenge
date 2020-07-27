package com.challenge.demo.repository;

import com.challenge.demo.entity.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {
    //List<QuestionAnswer> findBysiteId(Long siteId);
}
