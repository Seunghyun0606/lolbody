package com.ssafy.lolbody.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.lolbody.dto.ProfileDto;
import com.ssafy.lolbody.service.ProfileService;

import io.swagger.annotations.ApiOperation;

//http://localhost:8888/swagger-ui.html
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class ProfileController {
	@Autowired
	private ProfileService profileService;

	@ApiOperation(value = "소환사 이름으로 유저 프로필을 검색합니다. (성공:200, 실패:404)")
	@GetMapping("/profile/{name}")
	public ResponseEntity<ProfileDto> getProfile(@PathVariable String name) {
		ProfileDto profile = new ProfileDto();
		try {
			profile = profileService.getProfile(name);
			System.out.println(profile);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(profile, HttpStatus.OK);
	}
}
