package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
class TravelAgreementValidatorImpl implements TravelAgreementValidator {

    private final TravelAgreementFieldValidator agreementFieldValidator;
    private final TravelPersonFieldValidator personFieldValidator;

    TravelAgreementValidatorImpl(TravelAgreementFieldValidator agreementFieldValidator, 
                                 TravelPersonFieldValidator personFieldValidator) {
        this.agreementFieldValidator = agreementFieldValidator;
        this.personFieldValidator = personFieldValidator;
    }

    @Override
    public List<ValidationErrorDTO> validate(AgreementDTO agreement) {
        List<ValidationErrorDTO> agreementErrors = agreementFieldValidator.validate(agreement);
        List<ValidationErrorDTO> personErrors = personFieldValidator.validate(agreement.getPersons());
        return concatenateLists(agreementErrors, personErrors);
    }

    private List<ValidationErrorDTO> concatenateLists(List<ValidationErrorDTO> singleErrors,
                                                      List<ValidationErrorDTO> listErrors) {
        return Stream.concat(singleErrors.stream(), listErrors.stream())
                .collect(Collectors.toList());
    }

}
