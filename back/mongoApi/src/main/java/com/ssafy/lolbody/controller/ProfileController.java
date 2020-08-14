package com.ssafy.lolbody.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.lolbody.api.Api;
import com.ssafy.lolbody.dto.MatchRecordDto;
import com.ssafy.lolbody.dto.SummonerValueResultDto;
import com.ssafy.lolbody.dto.UserCardReferenceDto;
import com.ssafy.lolbody.service.ProfileService;
import com.ssafy.lolbody.service.SummonerValueService;

import io.swagger.annotations.ApiOperation;

//http://localhost:8888/swagger-ui.html
@CrossOrigin
@RestController
public class ProfileController {
	@Autowired
	private ProfileService profileService;
	@Autowired
	private SummonerValueService summonerValueService;

	@ApiOperation(value = "소환사 이름으로 유저 카드를 리턴받습니다.")
	@GetMapping("/api/profile/{name}")
	public ResponseEntity<UserCardReferenceDto> getUserCard(@PathVariable String name) {
		UserCardReferenceDto userCard = new UserCardReferenceDto();
		try {
			userCard = profileService.getUserCard(name.replaceAll(" ", ""));
		} catch (TimeoutException e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "유저 프로필 검색 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
		} catch (Exception e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "유저 프로필 검색 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(userCard, HttpStatus.OK);
	}
	
	@ApiOperation(value = "소환사 이름으로 유저 프로필을 갱신합니다.")
	@PutMapping("/api/profile/{name}")
	public ResponseEntity<UserCardReferenceDto> updateUserCard(@PathVariable String name) {
		UserCardReferenceDto userCard = new UserCardReferenceDto();
		try {
			profileService.updateProfile(name.replaceAll(" ", ""));
			userCard = profileService.getUserCard(name.replaceAll(" ", ""));
		} catch (TimeoutException e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "유저 프로필 검색 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
		} catch (Exception e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "유저 프로필 검색 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(userCard, HttpStatus.OK);
	}

	@ApiOperation(value = "소환사 이름으로 유저 매치 전적을 검색합니다. (num: 1부터 시작, 10개씩)")
	@GetMapping("/api/profile/{name}/{num}")
	public ResponseEntity<List<MatchRecordDto>> getMatchInfo(@PathVariable String name, @PathVariable String num) {
		List<MatchRecordDto> matchRecords = new ArrayList<>();
		try {
			matchRecords = profileService.getMatchRecord(name.replaceAll(" ", ""), num);
		} catch (TimeoutException e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "유저 매치 전적 검색 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
		} catch (Exception e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "유저 매치 전적 검색 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(matchRecords, HttpStatus.OK);
	}
	
	@ApiOperation(value = "소환사 이름으로 유저 성향을 검색합니다.")
	@GetMapping("/api/summonervalue/{name}")
	public ResponseEntity<SummonerValueResultDto> getSummonerValue(@PathVariable String name) {
		SummonerValueResultDto summonerValueResultDto;
		try {
			summonerValueResultDto = summonerValueService.getSummonerValue(name);
		} catch (TimeoutException e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "유저 성향 검색 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
		} catch (Exception e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "유저 성향 검색 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(summonerValueResultDto, HttpStatus.OK);
	}
	
}
