package com.ssafy.lolbody.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssafy.lolbody.dto.MatchRecordDto;

public interface MatchRecordRepository extends MongoRepository<MatchRecordDto, Long> {
	public MatchRecordDto findByGameId(long gameId);
}
