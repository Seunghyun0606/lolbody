package com.ssafy.lolbody.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssafy.lolbody.dto.MatchTimelineDto;

public interface MatchTimelineRepository extends MongoRepository<MatchTimelineDto, String> {
	public MatchTimelineDto findByGameId(long id);
}
