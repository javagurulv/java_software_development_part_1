package org.javaguru.travel.insurance.core.validations.person;

import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class EmptyPersonBirthDateValidation extends TravelPersonFieldValidationImpl {

    private final ValidationErrorFactory errorFactory;

    EmptyPersonBirthDateValidation(ValidationErrorFactory errorFactory) {
        this.errorFactory = errorFactory;
    }

    @Override
    public Optional<ValidationError> validate(TravelCalculatePremiumRequestV1 request) {
        return (personBirthDateIsNull(request))
                ? Optional.of(errorFactory.buildError("ERROR_CODE_11"))
                : Optional.empty();
    }

    private boolean personBirthDateIsNull(TravelCalculatePremiumRequestV1 request) {
        return request.getPersonBirthDate() == null;
    }

}
