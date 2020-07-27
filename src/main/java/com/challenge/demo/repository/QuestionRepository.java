package com.challenge.demo.repository;

import com.challenge.demo.entity.Question;
import com.challenge.demo.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

	@Query(value = "SELECT q.* FROM question as q WHERE q.site_id = ?1", nativeQuery = true)
	Optional<List<Question>> findQuestionsBySiteId(Long siteId);

	@Query(value = "SELECT q.* FROM question as q WHERE q.question_id = ?1", nativeQuery = true)
	Optional<Question> findQuestionByQuestionId(Long questionId);


}