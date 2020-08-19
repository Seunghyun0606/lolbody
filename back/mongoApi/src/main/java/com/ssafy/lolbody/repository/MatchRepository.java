package com.ssafy.lolbody.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssafy.lolbody.dto.MatchDto;

public interface MatchRepository extends MongoRepository<MatchDto, Long> {
	public MatchDto findByGameId(long gameId);
}
