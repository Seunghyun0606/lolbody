package com.ssafy.lolbody.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssafy.lolbody.dto.ProfileDto;

public interface ProfileRepository extends MongoRepository<ProfileDto, String> {
	public ProfileDto findBySummonerId(String id);
}
