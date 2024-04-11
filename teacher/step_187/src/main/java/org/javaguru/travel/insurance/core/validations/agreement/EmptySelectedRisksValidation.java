package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class EmptySelectedRisksValidation extends TravelAgreementFieldValidationImpl {

    private final ValidationErrorFactory errorFactory;

    EmptySelectedRisksValidation(ValidationErrorFactory errorFactory) {
        this.errorFactory = errorFactory;
    }

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement) {
        return (agreement.getSelectedRisks() == null || agreement.getSelectedRisks().isEmpty())
                ? Optional.of(errorFactory.buildError("ERROR_CODE_6"))
                : Optional.empty();
    }

}
