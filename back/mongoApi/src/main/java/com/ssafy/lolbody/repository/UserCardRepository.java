package com.ssafy.lolbody.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssafy.lolbody.dto.UserCardDto;

public interface UserCardRepository extends MongoRepository<UserCardDto, String> {
	public UserCardDto findBySummonerId(String id);
}
