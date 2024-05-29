package org.javaguru.travel.insurance.core.validations.agreement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class EmptyCountryValidation extends TravelAgreementFieldValidationImpl {

    private final ValidationErrorFactory errorFactory;

    @Override
    public Optional<ValidationError> validate(TravelCalculatePremiumRequest request) {
        return (countryIsNullOrBlank(request))
                ? Optional.of(errorFactory.buildError("ERROR_CODE_10"))
                : Optional.empty();
    }

    private boolean countryIsNullOrBlank(TravelCalculatePremiumRequest request) {
        return request.getCountry() == null || request.getCountry().isBlank();
    }

}
