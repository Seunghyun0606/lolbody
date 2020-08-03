package com.ssafy.lolbody.preset;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface QueueRepository extends MongoRepository<QueueDto, String> {
}
