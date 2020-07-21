package com.ssafy.apidemo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssafy.apidemo.dto.LeagueEntryDTO;

public interface LeagueEntryRepository extends MongoRepository<LeagueEntryDTO, String>{
	public List<LeagueEntryDTO> findBySummonerId(String summonerId);
}
