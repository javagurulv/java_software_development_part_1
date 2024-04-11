package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.domain.MedicalRiskLimitLevel;
import org.javaguru.travel.insurance.core.repositories.MedicalRiskLimitLevelRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class RiskLimitLevelCalculator {

    @Value( "${medical.risk.limit.level.enabled:false}" )
    private Boolean medicalRiskLimitLevelEnabled;

    private final MedicalRiskLimitLevelRepository riskLimitLevelRepository;

    RiskLimitLevelCalculator(MedicalRiskLimitLevelRepository riskLimitLevelRepository) {
        this.riskLimitLevelRepository = riskLimitLevelRepository;
    }

    BigDecimal calculate(AgreementDTO agreement) {
        return medicalRiskLimitLevelEnabled
                ? getCoefficient(agreement)
                : getDefaultValue();
    }

    private BigDecimal getCoefficient(AgreementDTO agreement) {
        return riskLimitLevelRepository.findByMedicalRiskLimitLevelIc(agreement.getMedicalRiskLimitLevel())
                .map(MedicalRiskLimitLevel::getCoefficient)
                .orElseThrow(() -> new RuntimeException("Medical risk limit level not found by = " + agreement.getMedicalRiskLimitLevel()));
    }

    private static BigDecimal getDefaultValue() {
        return BigDecimal.ONE;
    }

}
