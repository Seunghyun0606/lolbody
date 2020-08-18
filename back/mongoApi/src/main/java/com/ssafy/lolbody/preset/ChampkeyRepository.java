package com.ssafy.lolbody.preset;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChampkeyRepository extends MongoRepository<ChampKeyDto, String>{
	public ChampKeyDto findByKey(String key);
}
