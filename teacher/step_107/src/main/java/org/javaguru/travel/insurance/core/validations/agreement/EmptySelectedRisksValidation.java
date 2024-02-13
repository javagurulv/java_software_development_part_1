package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class EmptySelectedRisksValidation extends TravelAgreementFieldValidationImpl {

    @Autowired private ValidationErrorFactory errorFactory;

    @Override
    public Optional<ValidationError> validate(TravelCalculatePremiumRequest request) {
        return (request.getSelectedRisks() == null || request.getSelectedRisks().isEmpty())
                ? Optional.of(errorFactory.buildError("ERROR_CODE_6"))
                : Optional.empty();
    }

}
