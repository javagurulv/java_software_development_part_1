package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class EmptyCountryValidation extends TravelAgreementFieldValidationImpl {

    private final ValidationErrorFactory errorFactory;

    EmptyCountryValidation(ValidationErrorFactory errorFactory) {
        this.errorFactory = errorFactory;
    }

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement) {
        return (countryIsNullOrBlank(agreement))
                ? Optional.of(errorFactory.buildError("ERROR_CODE_10"))
                : Optional.empty();
    }

    private boolean countryIsNullOrBlank(AgreementDTO agreement) {
        return agreement.getCountry() == null || agreement.getCountry().isBlank();
    }

}
