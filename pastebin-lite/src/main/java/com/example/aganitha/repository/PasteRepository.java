package com.example.aganitha.repository;

import java.time.Instant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.aganitha.entity.Paste;

import jakarta.transaction.Transactional;

public interface PasteRepository extends JpaRepository<Paste, String>{
	   @Modifying
	    @Transactional
	    @Query("update Paste p"
	    		+ " set p.viewCount = p.viewCount + 1 "
	    		+ "where p.id = :id"
	    		+ " And (p.maxViews is null or p.viewCount < p.maxViews)"+
	    		"AND (p.expiresAt IS NULL OR p.expiresAt > :now)")
	    int incrementView(@Param("id")String id, @Param("now")Instant now);

}
