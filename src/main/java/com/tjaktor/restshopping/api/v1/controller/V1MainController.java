package com.tjaktor.restshopping.api.v1.controller;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tjaktor.restshopping.security.user.ApplicationUser;

@RestController
@RequestMapping("/api/v1")
@Profile("development")
public class V1MainController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public String index(@AuthenticationPrincipal ApplicationUser user) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").withZone(ZoneId.systemDefault());
		
		Instant now = Instant.now();
		
		String username = user.getUsername().substring(0, 1).toUpperCase() + user.getUsername().substring(1);
		
		LocalDateTime anotherTime = LocalDateTime.of(1983, Month.FEBRUARY, 16, 07, 13);
		Instant time2 = anotherTime.toInstant(ZoneOffset.UTC);
		
		long ns = Duration.between(now, time2).toHours();
		
		return "Hi, " + username + ". Index toimii. Aikaa kulunut: " + "(" + anotherTime.format(formatter) +" - " + formatter.format(now) + ") " + ns;
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	@ResponseBody
	public String adminIndex(Authentication auth) {
		
		ApplicationUser user = (ApplicationUser) auth.getPrincipal();
		
		String username = user.getUsername().substring(0, 1).toUpperCase() + user.getUsername().substring(1);
		
		return "Hi, " + username + ". Admin-index toimii";
	}
	
	@RequestMapping(value = "/admin/something", method = RequestMethod.GET)
	public ResponseEntity<?> returnSomething() {
		return new ResponseEntity<>("Ei näin.", HttpStatus.EXPECTATION_FAILED);
	}
	
}
