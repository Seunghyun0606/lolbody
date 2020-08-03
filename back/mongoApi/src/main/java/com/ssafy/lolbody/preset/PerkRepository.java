package com.ssafy.lolbody.preset;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PerkRepository extends MongoRepository<PerkDto, Integer>{
	public PerkDto findByKey(int key);
}
