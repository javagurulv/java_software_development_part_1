package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class DayCountCalculator {

    private final DateTimeUtil dateTimeUtil;

    DayCountCalculator(DateTimeUtil dateTimeUtil) {
        this.dateTimeUtil = dateTimeUtil;
    }

    BigDecimal calculate(TravelCalculatePremiumRequestV1 request) {
        var daysBetween = dateTimeUtil.getDaysBetween(request.getAgreementDateFrom(), request.getAgreementDateTo());
        return new BigDecimal(daysBetween);
    }

}
