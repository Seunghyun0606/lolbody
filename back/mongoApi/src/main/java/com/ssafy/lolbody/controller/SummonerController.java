package com.ssafy.lolbody.controller;

import java.util.List;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.lolbody.api.Api;
import com.ssafy.lolbody.dto.LeagueEntryDto;
import com.ssafy.lolbody.dto.MatchDto;
import com.ssafy.lolbody.dto.MatchlistDto;
import com.ssafy.lolbody.dto.SummonerDto;
import com.ssafy.lolbody.service.LeagueEntryService;
import com.ssafy.lolbody.service.MatchService;
import com.ssafy.lolbody.service.MatchlistService;
import com.ssafy.lolbody.service.SummonerService;

import io.swagger.annotations.ApiOperation;

//http://localhost:8888/swagger-ui.html
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
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
	public ResponseEntity<List<LeagueEntryDto>> getUserInfo(@PathVariable String name) {
		SummonerDto summonerDto = new SummonerDto();
		try {
			summonerDto = summonerService.findBySubName(name.replaceAll(" ", ""));
		} catch (TimeoutException e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "소환사 정보 검색 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
		} catch (Exception e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "소환사 정보 검색 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(leagueEntryService.findBySummonerId(summonerDto.getId()),HttpStatus.OK);
	}
	
	
	@GetMapping("/matchlist/{name}")
	@ApiOperation(value="사용자의 소환사 이름을 name 변수로 받아 매치 리스트를 검색합니다.")
	public ResponseEntity<MatchlistDto> getUserMatchlist(@PathVariable String name) {
		SummonerDto summonerDto = new SummonerDto();
		try {
			summonerDto = summonerService.findBySubName(name.replaceAll(" ", ""));
		} catch (TimeoutException e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "소환사 매치 리스트 검색 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
		} catch (Exception e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "소환사 매치 리스트 검색 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(matchlistService.findBySummonerId(summonerDto),HttpStatus.OK);
	}
	
	@GetMapping("/match/{gameId}")
	@ApiOperation(value="게임 id를 변수로 받아 매치 상세정보를 검색합니다.")
	public ResponseEntity<MatchDto> getUserMatch(@PathVariable long gameId) {
		MatchDto matchDto = new MatchDto();
		
		try {
			matchDto = matchService.findByGameId(gameId);
		} catch (TimeoutException e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "매치 상세정보 검색 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
		} catch (Exception e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "매치 상세정보 검색 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(matchDto,HttpStatus.OK);
	}
	
}
