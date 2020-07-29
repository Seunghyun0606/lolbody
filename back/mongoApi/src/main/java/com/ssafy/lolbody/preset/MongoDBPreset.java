package com.ssafy.lolbody.preset;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoDBPreset extends MongoRepository<ChampKeyDto, String>{
	public ChampKeyDto findByKey(String key);
}
