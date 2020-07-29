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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.lolbody.dto.MatchInfoDto;
import com.ssafy.lolbody.dto.ProfileReferenceDto;
import com.ssafy.lolbody.service.ProfileService;

import io.swagger.annotations.ApiOperation;

//http://localhost:8888/swagger-ui.html
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class ProfileController {
	@Autowired
	private ProfileService profileService;

	@ApiOperation(value = "소환사 이름으로 유저 프로필을 검색합니다.")
	@GetMapping("/profile/{name}")
	public ResponseEntity<ProfileReferenceDto> getProfile(@PathVariable String name) {
		ProfileReferenceDto profile = new ProfileReferenceDto();
		try {
			profile = profileService.getProfile(name.replaceAll(" ", ""));
		} catch (TimeoutException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(profile, HttpStatus.OK);
	}

	@ApiOperation(value = "소환사 이름으로 유저 매치 전적을 검색합니다. (num: 1부터 시작, 10개씩)")
	@GetMapping("/profile/{name}/{num}")
	public ResponseEntity<List<List<MatchInfoDto>>> getMatchInfo(@PathVariable String name, @PathVariable String num) {
		List<List<MatchInfoDto>> matchInfoList = new ArrayList<>();
		try {
			matchInfoList = profileService.getMatchInfo(name.replaceAll(" ", ""), num);
		} catch (TimeoutException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(matchInfoList, HttpStatus.OK);
	}

}
