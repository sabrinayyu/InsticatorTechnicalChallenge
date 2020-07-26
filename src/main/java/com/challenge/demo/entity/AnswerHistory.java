package com.challenge.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * user's answer history
 */
@Entity
@Table(name = "answer_history")
@EntityListeners(AuditingEntityListener.class)
public class AnswerHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "answer_history_id")
    private Long AnswerHistoryId;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Question question;

    @Column(nullable = false, name = "answer_round")
    private Integer answerRound;

    // the row answer chosen by the user
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_question_answer_id_row", referencedColumnName = "question_answer_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private QuestionAnswer userQuestionAnswerRow;

    // the col answer chosen by the user
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_question_answer_id_col", referencedColumnName = "question_answer_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private QuestionAnswer userQuestionAnswerCol;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate

    private Date createdAt;

    public Long getAnswerHistoryId() {
        return AnswerHistoryId;
    }

    public void setAnswerHistoryId(Long answerHistoryId) {
        AnswerHistoryId = answerHistoryId;
    }

    public User getUserId() {
        return user;
    }

    public void setUserId(final User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(final Question question) {
        this.question = question;
    }

    public Integer getAnswerRound() {
        return answerRound;
    }

    public void setAnswerRound(Integer answerRound) {
        this.answerRound = answerRound;
    }

    public QuestionAnswer getUserQuestionAnswerRow() {
        return userQuestionAnswerRow;
    }

    public void setUserQuestionAnswerRow(final QuestionAnswer userQuestionAnswerRow) {
        this.userQuestionAnswerRow = userQuestionAnswerRow;
    }

    public QuestionAnswer getUserQuestionAnswerCol() {
        return userQuestionAnswerCol;
    }

    public void setUserQuestionAnswerCol(final QuestionAnswer userQuestionAnswerCol) {
        this.userQuestionAnswerCol = userQuestionAnswerCol;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerHistory that = (AnswerHistory) o;
        return Objects.equals(AnswerHistoryId, that.AnswerHistoryId) &&
                Objects.equals(user, that.user) &&
                Objects.equals(question, that.question) &&
                Objects.equals(answerRound, that.answerRound) &&
                Objects.equals(userQuestionAnswerRow, that.userQuestionAnswerRow) &&
                Objects.equals(userQuestionAnswerCol, that.userQuestionAnswerCol) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(AnswerHistoryId, user, question, answerRound, userQuestionAnswerRow, userQuestionAnswerCol, createdAt);
    }
}
