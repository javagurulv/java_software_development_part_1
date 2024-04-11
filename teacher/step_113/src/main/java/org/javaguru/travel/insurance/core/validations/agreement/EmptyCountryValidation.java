package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class EmptyCountryValidation extends TravelAgreementFieldValidationImpl {

    private final ValidationErrorFactory errorFactory;

    EmptyCountryValidation(ValidationErrorFactory errorFactory) {
        this.errorFactory = errorFactory;
    }

    @Override
    public Optional<ValidationError> validate(TravelCalculatePremiumRequestV1 request) {
        return (countryIsNullOrBlank(request))
                ? Optional.of(errorFactory.buildError("ERROR_CODE_10"))
                : Optional.empty();
    }

    private boolean countryIsNullOrBlank(TravelCalculatePremiumRequestV1 request) {
        return request.getCountry() == null || request.getCountry().isBlank();
    }

}