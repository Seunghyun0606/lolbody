package com.ssafy.lolbody.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssafy.lolbody.dto.LeagueEntryDTO;

public interface LeagueEntryRepository extends MongoRepository<LeagueEntryDTO, String>{
	public List<LeagueEntryDTO> findBySummonerId(String summonerId);
}
