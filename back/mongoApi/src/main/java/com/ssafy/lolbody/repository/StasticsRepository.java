package com.ssafy.lolbody.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssafy.lolbody.dto.StasticsDto;

public interface StasticsRepository extends MongoRepository<StasticsDto, Long> {
	public StasticsDto findByQueueId(long queueId);
}
