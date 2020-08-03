package com.ssafy.lolbody.controller;

import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.lolbody.api.Api;
import com.ssafy.lolbody.dto.MultiSearchDto;
import com.ssafy.lolbody.service.MultiSearchService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
public class MultiSearchController {
	
	@Autowired
	private MultiSearchService multiSearchService;
	
	@GetMapping("/api/multisearch/{summonerName}")
	@ApiOperation(value="사용자의 소환사 이름을 name 변수로 받아 멀티서치에 필요한 정보를 반환합니다.")
	public ResponseEntity<MultiSearchDto> getMultiSearch(@PathVariable String summonerName) {
		MultiSearchDto multiSearchDto = new MultiSearchDto();
		try {
			multiSearchDto = multiSearchService.getMultiSearch(summonerName);
			
		} catch (TimeoutException e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "멀티서치 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
		} catch (Exception e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "멀티서치 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(multiSearchDto,HttpStatus.OK);
	}
}
