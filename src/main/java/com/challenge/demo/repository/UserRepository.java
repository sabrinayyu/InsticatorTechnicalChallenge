package com.challenge.demo.repository;

import com.challenge.demo.entity.AnswerHistory;
import com.challenge.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
