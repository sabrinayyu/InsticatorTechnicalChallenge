package com.challenge.demo.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
<<<<<<< Updated upstream
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
=======
import java.util.*;
>>>>>>> Stashed changes

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, name = "user_uuid")
    private UUID userUUID;

    @Column(nullable = false, name = "cur_answer_round", columnDefinition = "default 0")
    private Integer curAnswerRound;

<<<<<<< Updated upstream
=======
//    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
//    private List<AnswerHistory> answersHistory = new ArrayList<>();

>>>>>>> Stashed changes
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UUID getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(UUID userUUID) {
        this.userUUID = userUUID;
    }

<<<<<<< Updated upstream
    public Integer getAnswerRound() {
        return curAnswerRound;
    }

    public void setAnswerRound(Integer answerRound) {
=======
    public Integer getCurAnswerRound() {
        return curAnswerRound;
    }

    public void setCurAnswerRound(Integer answerRound) {
>>>>>>> Stashed changes
        this.curAnswerRound = answerRound;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(userUUID, user.userUUID) &&
                Objects.equals(curAnswerRound, user.curAnswerRound) &&
                Objects.equals(createdAt, user.createdAt) &&
                Objects.equals(updatedAt, user.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userUUID, curAnswerRound, createdAt, updatedAt);
    }
}
