package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.underwriting.TravelRiskPremiumCalculator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
class TravelMedicalRiskPremiumCalculator implements TravelRiskPremiumCalculator {

    private final DayCountCalculator dayCountCalculator;
    private final CountryDefaultDayRateCalculator countryDefaultDayRateCalculator;
    private final AgeCoefficientCalculator ageCoefficientCalculator;
    private final RiskLimitLevelCalculator riskLimitLevelCalculator;

    TravelMedicalRiskPremiumCalculator(DayCountCalculator dayCountCalculator,
                                       CountryDefaultDayRateCalculator countryDefaultDayRateCalculator,
                                       AgeCoefficientCalculator ageCoefficientCalculator,
                                       RiskLimitLevelCalculator riskLimitLevelCalculator) {
        this.dayCountCalculator = dayCountCalculator;
        this.countryDefaultDayRateCalculator = countryDefaultDayRateCalculator;
        this.ageCoefficientCalculator = ageCoefficientCalculator;
        this.riskLimitLevelCalculator = riskLimitLevelCalculator;
    }

    @Override
    public BigDecimal calculatePremium(AgreementDTO agreement, PersonDTO person) {
        var daysCount = dayCountCalculator.calculate(agreement);
        var countryDefaultRate = countryDefaultDayRateCalculator.calculate(agreement);
        var ageCoefficient = ageCoefficientCalculator.calculate(person);
        var riskLimitLevel = riskLimitLevelCalculator.calculate(person);
        return countryDefaultRate
                .multiply(daysCount)
                .multiply(ageCoefficient)
                .multiply(riskLimitLevel)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getRiskIc() {
        return "TRAVEL_MEDICAL";
    }

}
