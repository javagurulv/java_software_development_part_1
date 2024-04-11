package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
class CountryValidation extends TravelAgreementFieldValidationImpl {

    private final ClassifierValueRepository classifierValueRepository;
    private final ValidationErrorFactory errorFactory;

    CountryValidation(ClassifierValueRepository classifierValueRepository, 
                      ValidationErrorFactory errorFactory) {
        this.classifierValueRepository = classifierValueRepository;
        this.errorFactory = errorFactory;
    }

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement) {
        return (isCountryNotBlank(agreement))
                && !existInDatabase(agreement.getCountry())
                ? Optional.of(buildValidationError(agreement.getCountry()))
                : Optional.empty();
    }

    private ValidationErrorDTO buildValidationError(String country) {
        Placeholder placeholder = new Placeholder("NOT_SUPPORTED_COUNTRY", country);
        return errorFactory.buildError("ERROR_CODE_15", List.of(placeholder));
    }

    private boolean isCountryNotBlank(AgreementDTO agreement) {
        return agreement.getCountry() != null && !agreement.getCountry().isBlank();
    }

    private boolean existInDatabase(String countryIc) {
        return classifierValueRepository
                .findByClassifierTitleAndIc("COUNTRY", countryIc).isPresent();
    }

}
