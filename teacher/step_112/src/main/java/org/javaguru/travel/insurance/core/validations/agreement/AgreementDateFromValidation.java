package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class AgreementDateFromValidation extends TravelAgreementFieldValidationImpl {

    private final ValidationErrorFactory errorFactory;

    AgreementDateFromValidation(ValidationErrorFactory errorFactory) {
        this.errorFactory = errorFactory;
    }

    @Override
    public Optional<ValidationError> validate(TravelCalculatePremiumRequestV1 request) {
        return (request.getAgreementDateFrom() == null)
                ? Optional.of(errorFactory.buildError("ERROR_CODE_2"))
                : Optional.empty();
    }

}
