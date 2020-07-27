package com.challenge.demo.service;

import com.challenge.demo.dto.WholeQuestionDTO;
import com.challenge.demo.entity.*;
import com.challenge.demo.repository.*;
import com.challenge.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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
    UserRepository userRepository;

    @Autowired
    AnswerHistoryRepository answerHistoryRepository;

//    @Override
//    public boolean saveQuestionInfo(Object object) {
//
//        // 校验type是否存在枚举类中
//
//        // 校验questionId是否存在
//
//        // 校验question表是否存在
//        List<Question> siteQuestions = questionRepository.findSiteQuestions(0L).get();
//        if(Objects.nonNull(siteQuestions)){
//            return false;
//        }
//
//        // 保存question表
//        questionRepository.save(new Question());
//
//        // 保存questionAnswer表
//        questionAnswerRepository.save(new QuestionAnswer());
//
//        return true;
//
//    }

    @Override
    public WholeQuestionDTO getUniqueWholeQuestion(UUID siteUUID, UUID userUUID) {

        User user = getUserByUuid(userUUID);
        Site site = getSiteByUuid(siteUUID);

        //get the list of questions that the user hasn't seen in this round
        List<Question> siteQuestions = getSiteQuestionBySite(site);
        List<Question> completedQuestions = getCompletedQuestionByUser(user);

        List<Question> remainQuestions = new ArrayList<>(siteQuestions);
        remainQuestions.removeAll(completedQuestions);

         //if there are still questions remained in the siteQuestions List, return one
         //else update the current answer round in Use table and pick one from siteQuestions List
        Question returnQuestion = new Question();
        if (remainQuestions.size() > 1)
            returnQuestion = remainQuestions.get(0);
        else
            returnQuestion = siteQuestions.get(0);

        return getWholeQuestion(returnQuestion);
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

    public User getUserByUuid(UUID userUUID){
        //get user
        Optional<User> userFound = userRepository.findByUuid(userUUID);
        User user = new User();
        if (userFound.isPresent()) {
            user = userFound.get();
        } else {
            //save new user info
            user.setUserUUID(userUUID);
            user.setCurAnswerRound(1);
            user = userRepository.save(user);
        }
        return user;
    }

    public List<Question> getSiteQuestionBySite(Site site) {

        return questionRepository.findSiteQuestions(site.getSiteId()).get();
    }

    public List<Question> getCompletedQuestionByUser(User user) {
        List<Question> completedQuestions = (List<Question>) answerHistoryRepository
                .findByUserIdAndAnswerRound(user.getUserId(), user.getCurAnswerRound())
                .get()
                .stream()
                .map(answerHistory -> answerHistory.getQuestion())
                .collect(Collectors.toList());

        return completedQuestions;
    }

    public WholeQuestionDTO getWholeQuestion(Question question) {
        //todo not null check
        //todo try catch NoSuchElementException from get in controller
        //Question question = questionRepository.findById(questionId).get();
        return  WholeQuestionDTO.build(question, question.getAnswers());
    }

}
