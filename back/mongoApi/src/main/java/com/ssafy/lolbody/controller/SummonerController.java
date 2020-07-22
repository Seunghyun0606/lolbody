package com.ssafy.lolbody.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.lolbody.dto.LeagueEntryDTO;
import com.ssafy.lolbody.dto.MatchDto;
import com.ssafy.lolbody.dto.MatchlistDto;
import com.ssafy.lolbody.dto.SummonerDTO;
import com.ssafy.lolbody.service.LeagueEntryService;
import com.ssafy.lolbody.service.MatchService;
import com.ssafy.lolbody.service.MatchlistService;
import com.ssafy.lolbody.service.SummonerService;

import io.swagger.annotations.ApiOperation;

//http://localhost:8888/swagger-ui.html
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController("/api")
public class SummonerController {
	
	@Autowired
	private SummonerService summonerService;
	@Autowired
	private LeagueEntryService leagueEntryService;
	@Autowired
	private MatchlistService matchlistService;
	@Autowired
	private MatchService matchService;
	
	@GetMapping("/user/{name}")
	@ApiOperation(value="사용자의 소환사 이름을 name 변수로 받아 소환사 정보를 검색합니다.")
	public List<LeagueEntryDTO> getUserInfo(@PathVariable String name) {
		SummonerDTO summonerDto = summonerService.findByName(name);
		
		return leagueEntryService.findBySummonerId(summonerDto.getId());
	}
	
	
	@GetMapping("/matchlist/{name}")
	@ApiOperation(value="사용자의 소환사 이름을 name 변수로 받아 매치데이터를 검색합니다.")
	public MatchlistDto getUserMatchlist(@PathVariable String name) {
		SummonerDTO summonerDto = summonerService.findByName(name);
		
		return matchlistService.findBySummonerId(summonerDto);
	}
	
	@GetMapping("/match/{gameId}")
	@ApiOperation(value="게임 id를 변수로 받아 매치 상세정보를 검색합니다.")
	public MatchDto getUserMatch(@PathVariable String gameId) {
		return matchService.findByGameId(gameId);
	}
	
}
