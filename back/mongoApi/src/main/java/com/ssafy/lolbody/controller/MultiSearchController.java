package com.ssafy.lolbody.controller;

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
import com.ssafy.lolbody.dto.MultiSearchDto;
import com.ssafy.lolbody.service.ProfileService;

import io.swagger.annotations.ApiOperation;

//http://localhost:8888/swagger-ui.html
@CrossOrigin
@RestController
public class MultiSearchController {
	@Autowired
	private ProfileService profileService;

	@ApiOperation(value = "소환사 이름으로 멀티서치 정보를 반환합니다.")
	@GetMapping("/api/multisearch/{name}")
	public ResponseEntity<MultiSearchDto> getMatchInfo(@PathVariable String name) {
		MultiSearchDto multiSearch = new MultiSearchDto();
		try {
			profileService.updateProfile(name.replaceAll(" ", ""));
			multiSearch.setUserCard(profileService.getUserCard(name.replaceAll(" ", "")));
			multiSearch.setMatchResult(profileService.getMatchResult(name.replaceAll(" ", ""), "1"));
		} catch (TimeoutException e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "멀티서치 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "멀티서치 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "멀티서치 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(multiSearch, HttpStatus.OK);
	}

}
