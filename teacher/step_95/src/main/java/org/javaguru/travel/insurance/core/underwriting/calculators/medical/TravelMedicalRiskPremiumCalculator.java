package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.underwriting.TravelRiskPremiumCalculator;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
class TravelMedicalRiskPremiumCalculator implements TravelRiskPremiumCalculator {

    private final DayCountCalculator dayCountCalculator;
    private final CountryDefaultDayRateCalculator countryDefaultDayRateCalculator;
    private final AgeCoefficientCalculator ageCoefficientCalculator;

    TravelMedicalRiskPremiumCalculator(DayCountCalculator dayCountCalculator,
                                       CountryDefaultDayRateCalculator countryDefaultDayRateCalculator,
                                       AgeCoefficientCalculator ageCoefficientCalculator) {
        this.dayCountCalculator = dayCountCalculator;
        this.countryDefaultDayRateCalculator = countryDefaultDayRateCalculator;
        this.ageCoefficientCalculator = ageCoefficientCalculator;
    }

    @Override
    public BigDecimal calculatePremium(TravelCalculatePremiumRequest request) {
        var daysCount = dayCountCalculator.calculate(request);
        var countryDefaultRate = countryDefaultDayRateCalculator.calculate(request);
        var ageCoefficient = ageCoefficientCalculator.calculate(request);
        return countryDefaultRate
                .multiply(daysCount)
                .multiply(ageCoefficient)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getRiskIc() {
        return "TRAVEL_MEDICAL";
    }

}
