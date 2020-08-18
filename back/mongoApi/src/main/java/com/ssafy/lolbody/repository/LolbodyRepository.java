package com.ssafy.lolbody.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssafy.lolbody.dto.LolbodyResultDto;

public interface LolbodyRepository extends MongoRepository<LolbodyResultDto, String> {
	public LolbodyResultDto findBySummonerId(String id);
}
