package org.javaguru.travel.insurance.core.underwriting;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
class SelectedRisksPremiumCalculator {

    private final List<TravelRiskPremiumCalculator> riskPremiumCalculators;

    SelectedRisksPremiumCalculator(List<TravelRiskPremiumCalculator> riskPremiumCalculators) {
        this.riskPremiumCalculators = riskPremiumCalculators;
    }

    List<RiskDTO> calculatePremiumForAllRisks(AgreementDTO agreement, PersonDTO person) {
        return agreement.getSelectedRisks().stream()
                .map(riskIc -> new RiskDTO(riskIc, calculatePremiumForRisk(riskIc, agreement, person)))
                .toList();
    }

    private BigDecimal calculatePremiumForRisk(String riskIc, AgreementDTO agreement, PersonDTO person) {
        return findRiskPremiumCalculator(riskIc).calculatePremium(agreement, person);
    }

    private TravelRiskPremiumCalculator findRiskPremiumCalculator(String riskIc) {
        return riskPremiumCalculators.stream()
                .filter(riskCalculator -> riskCalculator.getRiskIc().equals(riskIc))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not supported riskIc = " + riskIc));
    }

}
