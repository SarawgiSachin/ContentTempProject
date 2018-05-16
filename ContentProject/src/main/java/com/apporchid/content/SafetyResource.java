package com.apporchid.content;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SafetyResource {
	@Inject
	SafetyService safetyService;

	@PostMapping({ "/safety360" })
	public ResponseEntity<?> getSafety360(@RequestParam("text") String text, @RequestParam("call") String call) {
		try {
			// Weather is freezing and conditions are slippery. Heavy traffic conditions at
			// site
			String response = safetyService.getSaftey360(text, call);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
