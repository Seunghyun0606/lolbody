package com.ssafy.lolbody.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.lolbody.dto.LeagueEntryDTO;
import com.ssafy.lolbody.dto.SummonerDTO;
import com.ssafy.lolbody.service.LeagueEntryService;
import com.ssafy.lolbody.service.SummonerService;

import io.swagger.annotations.ApiOperation;

@RestController("/api")
public class SummonerController {
	
	@Autowired
	private SummonerService summonerService;
	@Autowired
	private LeagueEntryService leagueEntryService;
	
	@GetMapping("/user/{name}")
	@ApiOperation(value="사용자의 소환사 이름을 name 변수로 받아 검색합니다.")
	public List<LeagueEntryDTO> getUserInfo(@PathVariable String name) {
		SummonerDTO summonerDto = summonerService.findByName(name);
		
		return leagueEntryService.findBySummonerId(summonerDto.getId());
	}
	
}
