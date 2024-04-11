package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.ErrorCodeUtil;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class AgreementDateToValidation implements TravelRequestValidation {

    private final ErrorCodeUtil errorCodeUtil;

    AgreementDateToValidation(ErrorCodeUtil errorCodeUtil) {
        this.errorCodeUtil = errorCodeUtil;
    }

    @Override
    public Optional<ValidationError> execute(TravelCalculatePremiumRequest request) {
        return (request.getAgreementDateTo() == null)
                ? Optional.of(buildError("ERROR_CODE_4"))
                : Optional.empty();
    }

    private ValidationError buildError(String errorCode) {
        String errorDescription = errorCodeUtil.getErrorDescription(errorCode);
        return new ValidationError(errorCode, errorDescription);
    }

}
