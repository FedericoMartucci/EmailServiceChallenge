package com.email.emailService.providers;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.email.emailService.model.StatsUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class StatsController {
	
	
	@PostMapping("/stats")
	public List<StatsUser> stats() {
		StatsService statsService = new StatsService();
		return statsService.getStats();
	}
}
