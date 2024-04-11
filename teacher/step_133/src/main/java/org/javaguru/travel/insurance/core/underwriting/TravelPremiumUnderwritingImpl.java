package org.javaguru.travel.insurance.core.underwriting;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
class TravelPremiumUnderwritingImpl implements TravelPremiumUnderwriting {

    private final SelectedRisksPremiumCalculator selectedRisksPremiumCalculator;

    TravelPremiumUnderwritingImpl(SelectedRisksPremiumCalculator selectedRisksPremiumCalculator) {
        this.selectedRisksPremiumCalculator = selectedRisksPremiumCalculator;
    }

    @Override
    public TravelPremiumCalculationResult calculatePremium(AgreementDTO agreementDTO, PersonDTO person) {
        List<RiskDTO> riskPremiums = calculateSelectedRisksPremium(agreementDTO, person);
        BigDecimal totalPremium = calculateTotalPremium(riskPremiums);
        return new TravelPremiumCalculationResult(totalPremium, riskPremiums);
    }

    private List<RiskDTO> calculateSelectedRisksPremium(AgreementDTO agreement, PersonDTO person) {
        return selectedRisksPremiumCalculator.calculatePremiumForAllRisks(agreement, person);
    }

    private static BigDecimal calculateTotalPremium(List<RiskDTO> risks) {
        return risks.stream()
                .map(RiskDTO::getPremium)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
