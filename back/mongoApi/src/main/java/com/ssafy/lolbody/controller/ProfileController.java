package com.ssafy.lolbody.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

//http://localhost:8888/swagger-ui.html
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController("/api/profile")
public class ProfileController {

	@ApiOperation(value = "소환사 이름으로 유저 정보를 검색합니다. (성공:200, 실패:404)")
	@GetMapping("/user/{id}")
	public ResponseEntity<String> test(String id) {
		return null;
	}
}
