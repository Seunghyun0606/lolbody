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
import com.ssafy.lolbody.dto.LolbodyDto;
import com.ssafy.lolbody.service.LolbodyService;

import io.swagger.annotations.ApiOperation;

//http://localhost:8888/swagger-ui.html
@CrossOrigin
@RestController
public class LolbodyController {
	@Autowired
	private LolbodyService lolbodyService;

	@ApiOperation(value = "소환사 이름으로 롤바디 결과를 리턴받습니다.")
	@GetMapping("/api/lolbody/{name}")
	public ResponseEntity<LolbodyDto> getUserCard(@PathVariable String name) {
		LolbodyDto lolbody = new LolbodyDto();
		try {
			lolbody = lolbodyService.getLolbody(name.replaceAll(" ", ""));
		} catch (TimeoutException e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "롤바디 검사 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "롤바디 검사 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			e.printStackTrace();
			Api.postHttpsRequest(e, "롤바디 검사 중 오류 발생");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(lolbody, HttpStatus.OK);
	}

}