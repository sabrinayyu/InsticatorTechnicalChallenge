package com.challenge.demo.repository;

import com.challenge.demo.entity.AnswerHistory;
<<<<<<< Updated upstream
import com.challenge.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
=======
import com.challenge.demo.entity.Site;
import com.challenge.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT u.* FROM user as u WHERE u.user_uuid = ?1", nativeQuery = true)
    Optional<User> findByUuid(UUID userUUID);

    //Optional<User> findByUserUUID(UUID userUUID);
>>>>>>> Stashed changes

}
