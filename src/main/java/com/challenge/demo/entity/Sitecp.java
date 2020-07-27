package com.challenge.demo.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "sitecp")
@EntityListeners(AuditingEntityListener.class)
public class Sitecp implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sitecp_id")
    private Long sitecpId;

    @Column(nullable = false, name = "sitecp_uuid")
    private UUID sitecpUUID;

    @Column(nullable = false, name = "cur_answer_round")
    private Integer curAnswerRound;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    public UUID getSitecpUUID() {
        return sitecpUUID;
    }

    public void setSitecpUUID(UUID sitecpUUID) {
        this.sitecpUUID = sitecpUUID;
    }

    public Long getSitecpId() {
        return sitecpId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Integer getCurAnswerRound() {
        return curAnswerRound;
    }

    public void setCurAnswerRound(final Integer curAnswerRound) {
        this.curAnswerRound = curAnswerRound;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Sitecp sitecp = (Sitecp) o;
        return Objects.equals(sitecpId, sitecp.sitecpId) &&
                Objects.equals(sitecpUUID, sitecp.sitecpUUID) &&
                Objects.equals(curAnswerRound, sitecp.curAnswerRound) &&
                Objects.equals(createdAt, sitecp.createdAt) &&
                Objects.equals(updatedAt, sitecp.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sitecpId, sitecpUUID, curAnswerRound, createdAt, updatedAt);
    }
}