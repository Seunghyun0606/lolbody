package com.ssafy.lolbody.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ssafy.lolbody.dto.LolbodyDto;
import com.ssafy.lolbody.dto.LolbodyResultDto;
import com.ssafy.lolbody.dto.SummonerDto;
import com.ssafy.lolbody.repository.LolbodyRepository;
import com.ssafy.lolbody.repository.StasticsRepository;

public class LolbodyService {
	@Autowired
	private LolbodyRepository lolbodyRepository;
	@Autowired
	private StasticsRepository stasticsRepository;
	@Autowired
	private SummonerService summonerService;
	@Autowired
	private ProfileService profileService;

	public LolbodyDto getLolbody(String name) throws Exception {
		SummonerDto summoner = summonerService.findOnly(name);
		LolbodyResultDto lolbodyResult = lolbodyRepository.findBySummonerId(summoner.getId());
		if (lolbodyResult == null) {
			updateLolbody(name);
			lolbodyResult = lolbodyRepository.findBySummonerId(summoner.getId());
		}
		List<LolbodyDto> lolbodyList = lolbodyResult.getLolbodyList();
		LolbodyDto lolbody = lolbodyList.get(lolbodyList.size() - 1);

		return lolbody;
	}

	public void updateLolbody(String name) throws Exception {
		profileService.updateProfile(name);

		SummonerDto summoner = summonerService.findOnly(name);
		LolbodyResultDto lolbodyResult = lolbodyRepository.findBySummonerId(summoner.getId());
		List<LolbodyDto> lolbodyList = new ArrayList<>();
		if (lolbodyResult == null) {
			lolbodyResult = new LolbodyResultDto();
			lolbodyResult.setSummonerId(summoner.getId());
		} else {
			lolbodyList = lolbodyResult.getLolbodyList();
		}
	}

}
