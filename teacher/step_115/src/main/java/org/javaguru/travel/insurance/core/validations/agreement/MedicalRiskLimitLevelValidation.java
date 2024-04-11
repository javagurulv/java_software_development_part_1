package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class MedicalRiskLimitLevelValidation extends TravelAgreementFieldValidationImpl {

    private final ClassifierValueRepository classifierValueRepository;
    private final ValidationErrorFactory errorFactory;

    MedicalRiskLimitLevelValidation(ClassifierValueRepository classifierValueRepository,
                                    ValidationErrorFactory errorFactory) {
        this.classifierValueRepository = classifierValueRepository;
        this.errorFactory = errorFactory;
    }

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement) {
        return (isMedicalRiskLimitLevelNotBlank(agreement))
                && !existInDatabase(agreement.getMedicalRiskLimitLevel())
                ? Optional.of(errorFactory.buildError("ERROR_CODE_14"))
                : Optional.empty();
    }

    private boolean isMedicalRiskLimitLevelNotBlank(AgreementDTO agreement) {
        return agreement.getMedicalRiskLimitLevel() != null && !agreement.getMedicalRiskLimitLevel().isBlank();
    }

    private boolean existInDatabase(String medicalRiscLimitLevelIc) {
        return classifierValueRepository
                .findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", medicalRiscLimitLevelIc).isPresent();
    }

}
