package com.ssafy.lolbody.controller;

import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.lolbody.api.Api;
import com.ssafy.lolbody.dto.MatchlistDto;
import com.ssafy.lolbody.dto.SummonerDto;
import com.ssafy.lolbody.service.MatchlistService;
import com.ssafy.lolbody.service.SummonerService;

import io.swagger.annotations.ApiOperation;

//http://localhost:8888/swagger-ui.html
@CrossOrigin
@RestController
public class SummonerController {

	@Autowired
	private SummonerService summonerService;
	@Autowired
	private MatchlistService matchlistService;

	@GetMapping("/api/user/{name}")
	@ApiOperation(value = "사용자의 소환사 이름을 name 변수로 받아 소환사 정보를 검색합니다.")
	public ResponseEntity<SummonerDto> getUserInfo(@PathVariable String name) {
		SummonerDto summonerDto = new SummonerDto();
		try {
			summonerDto = summonerService.findBySubName(name.replaceAll(" ", ""));
		} catch (TimeoutException e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "소환사 정보 검색 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "소환사 정보 검색 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "소환사 정보 검색 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(summonerDto, HttpStatus.OK);
	}

	@GetMapping("/api/matchlist/{name}")
	@ApiOperation(value = "사용자의 소환사 이름을 name 변수로 받아 매치 리스트를 검색합니다.")
	public ResponseEntity<MatchlistDto> getUserMatchlist(@PathVariable String name) {
		SummonerDto summonerDto = new SummonerDto();
		MatchlistDto matchlistDto = new MatchlistDto();
		try {
			summonerDto = summonerService.findBySubName(name.replaceAll(" ", ""));
			matchlistDto = matchlistService.findBySummonerId(summonerDto);
		} catch (TimeoutException e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "소환사 매치 리스트 검색 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "소환사 매치 리스트 검색 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "소환사 매치 리스트 검색 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(matchlistDto, HttpStatus.OK);
	}

	@GetMapping("/auto/{name}")
	@ApiOperation(value = "사용자의 소환사 이름을 name 변수로 받아 맞는 소환사 이름을 모두 반환합니다.")
	public ResponseEntity<List<SummonerDto>> getSummoners(@PathVariable String name) {
		return new ResponseEntity<>(summonerService.findByNameStartingWith(name), HttpStatus.OK);
	}

}
