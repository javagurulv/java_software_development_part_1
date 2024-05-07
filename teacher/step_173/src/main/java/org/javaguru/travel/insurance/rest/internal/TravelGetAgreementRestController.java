package org.javaguru.travel.insurance.rest.internal;

import com.google.common.base.Stopwatch;
import org.javaguru.travel.insurance.dto.internal.TravelGetAgreementResponse;
import org.javaguru.travel.insurance.rest.common.TravelRestRequestExecutionTimeLogger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/insurance/travel/api/internal/agreement")
public class TravelGetAgreementRestController {

	private final TravelGetAgreementRequestLogger requestLogger;
	private final TravelGetAgreementResponseLogger responseLogger;
	private final TravelRestRequestExecutionTimeLogger executionTimeLogger;

	TravelGetAgreementRestController(TravelGetAgreementRequestLogger requestLogger,
									 TravelGetAgreementResponseLogger responseLogger,
									 TravelRestRequestExecutionTimeLogger executionTimeLogger) {
		this.requestLogger = requestLogger;
		this.responseLogger = responseLogger;
		this.executionTimeLogger = executionTimeLogger;
	}

	@GetMapping(path = "/{uuid}",
			produces = "application/json")
	public TravelGetAgreementResponse getAgreement(@PathVariable("uuid") String uuid) {
		Stopwatch stopwatch = Stopwatch.createStarted();
		TravelGetAgreementResponse response = processRequest(uuid);
		new TravelGetAgreementResponse();
		response.setAgreementDateFrom(new Date());
		response.setAgreementDateTo(new Date());
		response.setUuid(uuid);
		executionTimeLogger.logExecutionTime(stopwatch);
		return response;
	}

	private TravelGetAgreementResponse processRequest(String uuid) {
		requestLogger.log(uuid);
		//TravelGetAgreementCoreCommand coreCommand = dtoConverter.buildCoreCommand(uuid);
		//TravelGetAgreementCoreResult coreResult = agreementService.getAgreement(coreCommand);
		//TravelGetAgreementResponse response = dtoV2Converter.buildResponse(coreResult);

		TravelGetAgreementResponse response = new TravelGetAgreementResponse();
		response.setAgreementDateFrom(new Date());
		response.setAgreementDateTo(new Date());
		response.setUuid(uuid);

		responseLogger.log(response);
		return response;
	}

}