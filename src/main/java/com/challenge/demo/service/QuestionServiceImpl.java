package com.challenge.demo.service;

import com.challenge.demo.dto.AnswerColRowDTO;
import com.challenge.demo.dto.WholeQuestionDTO;
import com.challenge.demo.entity.*;
import com.challenge.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionAnswerRepository questionAnswerRepository;

    @Autowired
    SiteRepository siteRepository;

    @Autowired
    SitecpRepository sitecpRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AnswerHistoryRepository answerHistoryRepository;


    @Override
    public WholeQuestionDTO getUniqueWholeQuestion(UUID siteUUID, UUID sitecpUUID) {

        Site site = getSiteByUuid(siteUUID);
        Sitecp sitecp = getSitecpByUuid(sitecpUUID);

        //get the list of questions that the user hasn't seen in this round
        List<Question> siteQuestions = getSiteQuestionBySite(site);
        List<Question> completedQuestions = getCompletedQuestionByUser(sitecp);

        List<Question> remainQuestions = new ArrayList<>(siteQuestions);
        remainQuestions.removeAll(completedQuestions);

         //if there are still questions remained in the siteQuestions List, return one
         //else update the current answer round in Use table and pick one from siteQuestions List
        Question returnQuestion = new Question();
        if (remainQuestions.size() > 1)
            returnQuestion = remainQuestions.get(0);
        else {
            //current question round ends, start next round
            sitecp.setCurAnswerRound(sitecp.getCurAnswerRound() + 1);
            sitecpRepository.save(sitecp);

            returnQuestion = siteQuestions.get(0);
        }

        return getWholeQuestion(returnQuestion);
    }

    @Override
    public List<AnswerHistory> createAnswerHistory(UUID sitecpUUID, Long questionId, List<AnswerColRowDTO> userAnswers) {
        //prepare all attributes for AnswerHistory
        Sitecp sitecp = getSitecpByUuid(sitecpUUID);
        Integer answerRound = sitecp.getCurAnswerRound();
        Question question = getQuestionByquestionId(questionId);

        List<AnswerHistory> answerHistories = new ArrayList<>();
        for (AnswerColRowDTO answerColRowDTO : userAnswers) {
            //prepare all attributes
            QuestionAnswer questionAnswerRow = getQAbyQuestionAnswerId(answerColRowDTO.getAnswerRowId());
            QuestionAnswer questionAnswerCol = getQAbyQuestionAnswerId(answerColRowDTO.getAnswerColId());


            //save new Record in ANSWERHISTORY table
            AnswerHistory answerHistory = new AnswerHistory();
            answerHistory.setQuestion(question);
            answerHistory.setUserId(sitecp);
            answerHistory.setUserQuestionAnswerRow(questionAnswerRow);
            answerHistory.setUserQuestionAnswerCol(questionAnswerCol);
            answerHistory.setAnswerRound(answerRound);
            answerHistory = answerHistoryRepository.save(answerHistory);

            answerHistories.add(answerHistory);
        }

        return answerHistories;
    }

    public QuestionAnswer getQAbyQuestionAnswerId(Long questionAnswerId) {
        Optional<QuestionAnswer> questionAnswerFound =
                questionAnswerRepository.findQAByQuestionAnswerId(questionAnswerId);
        QuestionAnswer questionAnswer = new QuestionAnswer();
        if (questionAnswerFound.isPresent()) {
            questionAnswer = questionAnswerFound.get();
        } else {
            throw new NoSuchElementException("No questionAnswer found, wrong questionAnswer id");
        }

        return questionAnswer;
    }

    public Question getQuestionByquestionId(Long questionId) {
        Optional<Question> questionFound = questionRepository.findQuestionByQuestionId(questionId);
        Question question = new Question();
        if (questionFound.isPresent()) {
            question = questionFound.get();
        } else {
            throw new NoSuchElementException("No question found, wrong question id");
        }
        return question;
    }

    public Site getSiteByUuid(UUID siteUUID) {
        //get site
        Optional<Site> siteFound = siteRepository.findByUuid(siteUUID);
        Site site = new Site();
        if (siteFound.isPresent()) {
            site = siteFound.get();
        } else {
            throw new NoSuchElementException("No site found, maybe create website first");
        }
        return site;
    }

    public Sitecp getSitecpByUuid(UUID sitecpUUID){
        //get user
//        Optional<User> userFound = userRepository.findByUuid(userUUID);
//        User user = new User();
//        if (userFound.isPresent()) {
//            user = userFound.get();
//        } else {
//            //save new user info
//            user.setUserUUID(userUUID);
//            user.setCurAnswerRound(1);
//            user = userRepository.save(user);
//        }
//        return user;
        Optional<Sitecp> sitecpFound = sitecpRepository.findByUuid(sitecpUUID);
        Sitecp sitecp = new Sitecp();
        if (sitecpFound.isPresent()) {
            sitecp = sitecpFound.get();
        } else {
            //save new user info
            sitecp.setSitecpUUID(sitecpUUID);
            sitecp.setCurAnswerRound(1);
            sitecp = sitecpRepository.save(sitecp);
        }
        return sitecp;
    }

    public List<Question> getSiteQuestionBySite(Site site) {

        return questionRepository.findQuestionsBySiteId(site.getSiteId()).get();
    }

    public List<Question> getCompletedQuestionByUser(Sitecp sitecp) {
//        List<Question> completedQuestions = (List<Question>) answerHistoryRepository
//                .findByUserIdAndAnswerRound(sitecp.getUserId(), sitecp.getCurAnswerRound())
//                .get()
//                .stream()
//                .map(answerHistory -> answerHistory.getQuestion())
//                .collect(Collectors.toList());
        List<Question> completedQuestions = new ArrayList<>();
        Optional<List<AnswerHistory>> completedQuestionsFound =
                answerHistoryRepository.findByUserIdAndAnswerRound(sitecp.getSitecpId(),sitecp.getCurAnswerRound());

        if (completedQuestionsFound.isPresent()) {
            completedQuestions = completedQuestionsFound
                    .get()
                    .stream()
                    .map(answerHistory -> answerHistory.getQuestion())
                    .collect(Collectors.toList());
        }

        return completedQuestions;
    }

    public WholeQuestionDTO getWholeQuestion(Question question) {
        if (question == null)
            return null;

        return  WholeQuestionDTO.build(question, question.getAnswers());
    }



}
