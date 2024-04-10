package org.javaguru.travel.insurance.core;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class TravelCalculatePremiumRequestValidator {

    private final PersonFirstNameValidation personFirstNameValidation;
    private final PersonLastNameValidation personLastNameValidation;
    private final AgreementDateFromValidation agreementDateFromValidation;
    private final AgreementDateToValidation agreementDateToValidation;
    private final DateFromLessThenDateToValidation dateFromLessThenDateToValidation;
    private final AgreementDateFromInFutureValidation agreementDateFromInFutureValidation;
    private final AgreementDateToInFutureValidation agreementDateToInFutureValidation;

    TravelCalculatePremiumRequestValidator(PersonFirstNameValidation personFirstNameValidation,
                                           PersonLastNameValidation personLastNameValidation,
                                           AgreementDateFromValidation agreementDateFromValidation,
                                           AgreementDateToValidation agreementDateToValidation,
                                           DateFromLessThenDateToValidation dateFromLessThenDateToValidation,
                                           AgreementDateFromInFutureValidation agreementDateFromInFutureValidation,
                                           AgreementDateToInFutureValidation agreementDateToInFutureValidation) {
        this.personFirstNameValidation = personFirstNameValidation;
        this.personLastNameValidation = personLastNameValidation;
        this.agreementDateFromValidation = agreementDateFromValidation;
        this.agreementDateToValidation = agreementDateToValidation;
        this.dateFromLessThenDateToValidation = dateFromLessThenDateToValidation;
        this.agreementDateFromInFutureValidation = agreementDateFromInFutureValidation;
        this.agreementDateToInFutureValidation = agreementDateToInFutureValidation;
    }

    public List<ValidationError> validate(TravelCalculatePremiumRequest request) {
        List<ValidationError> errors = new ArrayList<>();
        personFirstNameValidation.validatePersonFirstName(request).ifPresent(errors::add);
        personLastNameValidation.validatePersonLastName(request).ifPresent(errors::add);
        agreementDateFromValidation.validateAgreementDateFrom(request).ifPresent(errors::add);
        agreementDateToValidation.validateAgreementDateTo(request).ifPresent(errors::add);
        dateFromLessThenDateToValidation.validateDateFromLessThenDateTo(request).ifPresent(errors::add);
        agreementDateFromInFutureValidation.validateDateFromInFuture(request).ifPresent(errors::add);
        agreementDateToInFutureValidation.validateDateToInFuture(request).ifPresent(errors::add);
        return errors;
    }

}
