package com.challenge.demo.controller;

import com.challenge.demo.dto.*;
import com.challenge.demo.entity.*;
import com.challenge.demo.repository.QuestionAnswerRepository;
import com.challenge.demo.repository.QuestionRepository;
import com.challenge.demo.repository.SiteRepository;
import com.challenge.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	SiteRepository siteRepository;

	@Autowired
	QuestionAnswerRepository qaRepository;

	@Autowired
	QuestionService questionService;


	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionDTO incomingQuestion) {
		return siteRepository
				.findById(incomingQuestion.getSiteId())
				.map(site -> {
					try {
						final Question newQ;
						newQ = QuestionDTO.createQuestion(incomingQuestion, site);
						return new ResponseEntity<>(QuestionDTO.build(questionRepository.save(newQ)), HttpStatus.CREATED);
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
						return new ResponseEntity<>(new QuestionDTO(), HttpStatus.BAD_REQUEST);
					}
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping()
	public ResponseEntity<List<QuestionDTO>> getSites() {
		return Optional
				.ofNullable(questionRepository.findAll())
				.map(questions -> ResponseEntity.ok(QuestionDTO.build(questions)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<QuestionDTO> updateQuestion(@RequestBody Question incomingQuestion, @PathVariable(value = "id") Long questionId) {

		return questionRepository
				.findById(questionId)
				.map(question -> {
					question.setQuestion(incomingQuestion.getQuestion());
					question.setSite(incomingQuestion.getSite());
					return new ResponseEntity<>(QuestionDTO.build(questionRepository.save(question)), HttpStatus.OK);
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<QuestionDTO> deleteQuestion(@PathVariable(value = "id") Long questionId) {
		return questionRepository
				.findById(questionId)
				.map(question -> {
					questionRepository.delete(question);
					return ResponseEntity.ok(QuestionDTO.build(question));
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/{id}")
	public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable(value = "id") Long questionId) {
		return questionRepository
				.findById(questionId)
				.map(question -> ResponseEntity.ok(QuestionDTO.build(question)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/{id}/answers")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<QuestionAnswerDTO> createQuestionAnswers(@PathVariable(value = "id") Long questionId,
																   @RequestBody QuestionAnswerDTO newQADto) {
		return questionRepository
				.findById(questionId)
				.map(question -> {
					final QuestionAnswer newQa = QuestionAnswerDTO.transform(newQADto, question);
					return new ResponseEntity<>(QuestionAnswerDTO.build(qaRepository.save(newQa)), HttpStatus.CREATED);
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/{id}/answers")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<QuestionAnswerDTO>> getQuestionAnswers(@PathVariable(value = "id") Long questionId) {
		return questionRepository
				.findById(questionId)
				.map(question -> ResponseEntity.ok(QuestionAnswerDTO.build(question.getAnswers())))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}


//	//test wholequestion, worked
//	@GetMapping("/{id}/wholequestion")
//	@ResponseStatus(HttpStatus.OK)
//	public ResponseEntity<WholeQuestionDTO> getWholeQuestion(@PathVariable(value = "id") Long questionId) {
//		return questionRepository
//				.findById(questionId)
//				.map(question -> ResponseEntity.ok(WholeQuestionDTO.build(question, question.getAnswers())))
//				.orElseGet(() -> ResponseEntity.notFound().build());
//	}


	@PostMapping("/unique")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<WholeQuestionDTO> getUniqueQuestion(@RequestBody PostUniqueDTO postUniqueDTO) {
		try {
			//format change
			UUID siteUUID = UUID.fromString(postUniqueDTO.getSiteUUID());
			UUID sitecpUUID = UUID.fromString(postUniqueDTO.getSitecpUUID());
			WholeQuestionDTO result = questionService.getUniqueWholeQuestion(siteUUID, sitecpUUID);

			// no question stored for the site
			if (result == null) {
				System.out.println("No question stored for the site /questions/unique ");
				return ResponseEntity.noContent().build();
			}

			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("Null in requestBody of /questions/unique ");
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("There is something wrong for /questions/unique " + e.getMessage());
			return ResponseEntity.notFound().build();
		}

	}

	//todo
	@PostMapping("/useranswer")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<PostUserAnswerDTO> createAnswerHistory(@RequestBody PostUserAnswerDTO postUserAnswerDTO) {
		try {
			UUID sitecpUUID = UUID.fromString(postUserAnswerDTO.getUserUUID());
			questionService.createAnswerHistory(
					sitecpUUID, postUserAnswerDTO.getQuestionId(), postUserAnswerDTO.getUserAnswers());

			return new ResponseEntity<>(postUserAnswerDTO, HttpStatus.CREATED);
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("Null in requestBody of /questions/useranswer ");
			return ResponseEntity.badRequest().build();
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("There is something wrong for /questions/useranswer " + e.getMessage());
			return ResponseEntity.notFound().build();
		}
	}

}

