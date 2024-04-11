package org.javaguru.travel.insurance.core.underwriting;

import org.javaguru.travel.insurance.dto.RiskPremium;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;

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
    public TravelPremiumCalculationResult calculatePremium(TravelCalculatePremiumRequestV1 request) {
        List<RiskPremium> riskPremiums = calculateSelectedRisksPremium(request);
        BigDecimal totalPremium = calculateTotalPremium(riskPremiums);
        return new TravelPremiumCalculationResult(totalPremium, riskPremiums);
    }

    private List<RiskPremium> calculateSelectedRisksPremium(TravelCalculatePremiumRequestV1 request) {
        return selectedRisksPremiumCalculator.calculatePremiumForAllRisks(request);
    }

    private static BigDecimal calculateTotalPremium(List<RiskPremium> riskPremiums) {
        return riskPremiums.stream()
                .map(RiskPremium::getPremium)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
