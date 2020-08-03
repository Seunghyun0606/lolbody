package com.ssafy.lolbody.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.lolbody.dto.RadarChartDto;
import com.ssafy.lolbody.service.RadarChartService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
public class RadarChartController {
	
	@Autowired
	private RadarChartService radarChartService;

	@GetMapping("/api/radar/{summonerName}")
	@ApiOperation(value="사용자의 소환사 이름을 name 변수로 받아 radar_chart에 필요한 정보를 반환합니다.")
	public ResponseEntity<RadarChartDto> getRadarChart(@PathVariable String summonerName) {
		RadarChartDto radarChart = new RadarChartDto();
		try {
			radarChart = radarChartService.getRadarChar(summonerName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(radarChart,HttpStatus.OK);
	}
}
