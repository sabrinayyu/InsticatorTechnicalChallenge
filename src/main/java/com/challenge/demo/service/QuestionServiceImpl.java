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
import java.util.stream.Collector;
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

        return questionRepository.findSiteQuestions(site.getSiteId()).get();
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
                answerHistoryRepository.findBySitecpId(sitecp.getSitecpId());

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
