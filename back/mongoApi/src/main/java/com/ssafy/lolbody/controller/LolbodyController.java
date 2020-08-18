package com.ssafy.lolbody.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.lolbody.dto.StasticsDto;
import com.ssafy.lolbody.repository.StasticsRepository;

import io.swagger.annotations.ApiOperation;

//http://localhost:8888/swagger-ui.html
@CrossOrigin
@RestController
public class LolbodyController {
	@Autowired
	private StasticsRepository tmp;
	
	@ApiOperation(value = "소환사 이름으로 유저 카드를 리턴받습니다.")
	@GetMapping("/api/test/{queue}")
	public ResponseEntity<StasticsDto> getUserCard(@PathVariable String queue) {
		return new ResponseEntity<>(tmp.findByQueueId(Long.parseLong(queue)), HttpStatus.OK);
	}

}