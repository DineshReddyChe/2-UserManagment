package com.dinesh.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dinesh.dto.QuoteApiResponseDto;
import com.dinesh.service.DashboardService;
@Service
public class DashBoardServiceImpl implements DashboardService {

	private String quoteApiurl="https://dummyjson.com/quotes/random";
	@Override
	public QuoteApiResponseDto getQuote() {
		RestTemplate trt=new RestTemplate();
		ResponseEntity<QuoteApiResponseDto> forEntity=trt.getForEntity(quoteApiurl, QuoteApiResponseDto.class);
		QuoteApiResponseDto body=forEntity.getBody();
		return body;
	}

}
