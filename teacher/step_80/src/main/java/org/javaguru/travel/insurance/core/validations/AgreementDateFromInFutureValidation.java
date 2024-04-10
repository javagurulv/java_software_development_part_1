package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
class AgreementDateFromInFutureValidation extends TravelRequestValidationImpl {

    private final DateTimeUtil dateTimeUtil;
    private final ValidationErrorFactory errorFactory;

    AgreementDateFromInFutureValidation(DateTimeUtil dateTimeUtil,
                                        ValidationErrorFactory errorFactory) {
        this.dateTimeUtil = dateTimeUtil;
        this.errorFactory = errorFactory;
    }

    @Override
    public Optional<ValidationError> validate(TravelCalculatePremiumRequest request) {
        Date dateFrom = request.getAgreementDateFrom();
        Date currentDateTime = dateTimeUtil.getCurrentDateTime();
        return (dateFrom != null && dateFrom.before(currentDateTime))
                ? Optional.of(errorFactory.buildError("ERROR_CODE_1"))
                : Optional.empty();
    }

}
