package org.javaguru.travel.insurance.core.services;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.underwriting.TravelPremiumCalculationResult;
import org.javaguru.travel.insurance.core.underwriting.TravelPremiumUnderwriting;

import org.springframework.stereotype.Component;

@Component
class AgreementPersonsPremiumCalculator {

    private final TravelPremiumUnderwriting premiumUnderwriting;

    AgreementPersonsPremiumCalculator(TravelPremiumUnderwriting premiumUnderwriting) {
        this.premiumUnderwriting = premiumUnderwriting;
    }

    void calculateRiskPremiums(AgreementDTO agreement) {
        agreement.getPersons().forEach(person -> {
            TravelPremiumCalculationResult calculationResult = premiumUnderwriting.calculatePremium(agreement, person);
            person.setRisks(calculationResult.getRisks());
        });
    }

}
