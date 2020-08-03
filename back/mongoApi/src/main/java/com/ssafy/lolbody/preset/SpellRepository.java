package com.ssafy.lolbody.preset;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpellRepository extends MongoRepository<SpellDto, String>{
	public SpellDto findByKey(String key);
}
