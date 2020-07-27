package com.challenge.demo.repository;

import com.challenge.demo.entity.Site;
import com.challenge.demo.entity.Sitecp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface SitecpRepository extends JpaRepository<Sitecp, Long> {
    
    @Query(value = "SELECT sc.* FROM Sitecp as sc WHERE sc.sitecp_uuid = ?1", nativeQuery = true)
    Optional<Sitecp> findByUuid(UUID sitecpUUID);

    //Optional<Sitecp> findByUUID(UUID sitecpUUID);
}
