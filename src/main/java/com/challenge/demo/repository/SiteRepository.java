package com.challenge.demo.repository;

import com.challenge.demo.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface SiteRepository extends JpaRepository<Site, Long> {

	@Query(value = "SELECT s.* FROM Sites s WHERE s.site_uuid = ?1", nativeQuery = true)
	Site findByUuid(UUID siteUUID);
}