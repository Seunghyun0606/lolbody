package com.ssafy.lolbody.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssafy.lolbody.dto.LeagueEntryDto;

public interface LeagueEntryRepository extends MongoRepository<LeagueEntryDto, String>{
	public List<LeagueEntryDto> findBySummonerId(String summonerId);
}
