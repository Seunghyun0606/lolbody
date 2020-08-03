package com.ssafy.lolbody.preset;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PerkStyleRepository extends MongoRepository<PerkStyleDto, Integer>{
	public PerkStyleDto findByKey(int key);
}
