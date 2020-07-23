package com.ssafy.lolbody.preset;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoDBPreset extends MongoRepository<ChampKeyDTO, String>{
}
