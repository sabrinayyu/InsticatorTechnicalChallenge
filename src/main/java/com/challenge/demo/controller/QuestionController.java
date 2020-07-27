package com.challenge.demo.controller;

import com.challenge.demo.dto.QuestionAnswerDTO;
import com.challenge.demo.dto.QuestionDTO;
import com.challenge.demo.dto.UniqueGeneratorParamDTO;
import com.challenge.demo.dto.WholeQuestionDTO;
import com.challenge.demo.entity.Question;
import com.challenge.demo.entity.QuestionAnswer;
import com.challenge.demo.entity.Site;
import com.challenge.demo.repository.QuestionAnswerRepository;
import com.challenge.demo.repository.QuestionRepository;
import com.challenge.demo.repository.SiteRepository;
import com.challenge.demo.repository.UserRepository;
import com.challenge.demo.service.QuestionService;
import com.challenge.demo.service.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
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

//	@Autowired
//	UserRepository userRepository;

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


// user table could not be added
//	@PostMapping("/unique")
//	public ResponseEntity<WholeQuestionDTO> getUniqueQuestion(@RequestBody UniqueGeneratorParamDTO uniqueGeneratorParamDTO) {
//		try {
//			UUID siteUUID = UUID.fromString(uniqueGeneratorParamDTO.getSiteUUID());
//			UUID userUUID = UUID.fromString(uniqueGeneratorParamDTO.getUserUUID());
//			System.out.println("1 siteUUID:" + siteUUID);
//			return ResponseEntity.ok(questionService.getUniqueWholeQuestion(siteUUID, userUUID));
//		} catch (NullPointerException e) {
//			System.out.println("Null in requestBody of /unique ");
//		} catch (Exception e) {
//			System.out.println("There is something wrong for /questions/unique" + e.getMessage());
//			return ResponseEntity.badRequest().build();
//		}
//
//	}

	//todo
	//@PostMapping("/useranswer")

	}

