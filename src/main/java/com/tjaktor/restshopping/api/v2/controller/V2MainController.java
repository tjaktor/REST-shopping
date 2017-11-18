package com.tjaktor.restshopping.api.v2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2")
public class V2MainController {

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> index() {
		return new ResponseEntity<>("Example. API version 2.", HttpStatus.OK);
	}
	
}
