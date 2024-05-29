package org.javaguru.travel.insurance.rest;

import com.google.common.base.Stopwatch;
import org.javaguru.travel.insurance.core.services.TravelCalculatePremiumService;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insurance/travel")
public class TravelCalculatePremiumController {

	private final TravelCalculatePremiumRequestLogger requestLogger;
	private final TravelCalculatePremiumResponseLogger responseLogger;
	private final TravelCalculatePremiumRequestExecutionTimeLogger executionTimeLogger;
	private final TravelCalculatePremiumService calculatePremiumService;

	TravelCalculatePremiumController(TravelCalculatePremiumRequestLogger requestLogger,
									 TravelCalculatePremiumResponseLogger responseLogger,
									 TravelCalculatePremiumRequestExecutionTimeLogger executionTimeLogger,
									 TravelCalculatePremiumService calculatePremiumService) {
		this.requestLogger = requestLogger;
		this.responseLogger = responseLogger;
		this.executionTimeLogger = executionTimeLogger;
		this.calculatePremiumService = calculatePremiumService;
	}

	@PostMapping(path = "/",
			consumes = "application/json",
			produces = "application/json")
	public TravelCalculatePremiumResponse calculatePremium(@RequestBody TravelCalculatePremiumRequest request) {
		Stopwatch stopwatch = Stopwatch.createStarted();
		TravelCalculatePremiumResponse response = processRequest(request);
		executionTimeLogger.logExecutionTime(stopwatch);
		return response;
	}

	private TravelCalculatePremiumResponse processRequest(TravelCalculatePremiumRequest request) {
		requestLogger.log(request);
		TravelCalculatePremiumResponse response = calculatePremiumService.calculatePremium(request);
		responseLogger.log(response);
		return response;
	}

}