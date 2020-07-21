package com.ssafy.lolbody.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssafy.lolbody.dto.MatchlistDto;

public interface MatchlistRepository extends MongoRepository<MatchlistDto, String> {
	public MatchlistDto findBySummonerId(String id);
}
