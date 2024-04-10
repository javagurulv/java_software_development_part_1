package org.javaguru.travel.insurance.core;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class TravelPremiumUnderwriting {

    private final DateTimeService dateTimeService;

    TravelPremiumUnderwriting(DateTimeService dateTimeService) {
        this.dateTimeService = dateTimeService;
    }

    BigDecimal calculatePremium(TravelCalculatePremiumRequest request) {
        var daysBetween = dateTimeService.getDaysBetween(request.getAgreementDateFrom(), request.getAgreementDateTo());
        return new BigDecimal(daysBetween);
    }

}
