package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

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
    public Optional<ValidationError> validate(TravelCalculatePremiumRequest request) {
        return (isCountryNotBlank(request))
                && !existInDatabase(request.getCountry())
                ? Optional.of(errorFactory.buildError("ERROR_CODE_15"))
                : Optional.empty();
    }

    private boolean isCountryNotBlank(TravelCalculatePremiumRequest request) {
        return request.getCountry() != null && !request.getCountry().isBlank();
    }

    private boolean existInDatabase(String countryIc) {
        return classifierValueRepository
                .findByClassifierTitleAndIc("COUNTRY", countryIc).isPresent();
    }

}
