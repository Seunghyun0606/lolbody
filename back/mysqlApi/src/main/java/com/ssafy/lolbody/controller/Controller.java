package com.ssafy.lolbody.controller;

import org.json.simple.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.lolbody.dto.SummonerDTO;
import com.ssafy.lolbody.service.SummonerService;

import io.swagger.annotations.ApiOperation;

//http://localhost:9999/swagger-ui.html
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class Controller {
	@Autowired
	SummonerService summonerService;

	@ApiOperation(value = "소환사 이름으로 유저 정보를 검색합니다. (성공:200, 실패:404)")
	@GetMapping("/user/{summonerName}")
	public ResponseEntity<SummonerDTO> getSummoner(@PathVariable String summonerName) throws Exception {
		SummonerDTO summonerDto = summonerService.getSummoner(summonerName);
		if (summonerDto.getName() != null)
			return new ResponseEntity<>(summonerDto, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "소환사 이름으로 유저 티어를 검색합니다.")
	@GetMapping("/tier/{summonerName}")
	public ResponseEntity<JSONArray> getSummonerTier(@PathVariable String summonerName) throws Exception {
		return new ResponseEntity<>(summonerService.getSummonerTier(summonerName), HttpStatus.OK);
	}

	@ApiOperation(value = "소환사 이름으로 유저 전적을 검색합니다.")
	@GetMapping("/match/{summonerName}")
	public ResponseEntity<JSONObject> getSummonerMatch(@PathVariable String summonerName) throws Exception {
		return new ResponseEntity<>(summonerService.getSummonerMatch(summonerName), HttpStatus.OK);
	}
}
