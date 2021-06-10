package com.memoable.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class for catching REST requests to test connections to the backend
 * @author Jere Salmensaari
 */
@RestController
public class PingController {

	/**
	 * Gets requests to the endpoint and returns "pong"
	 * @return "pong"
	 */
	@GetMapping("/ping")
	public String ping() {
		return "pong";
	}
}
