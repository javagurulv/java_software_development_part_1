package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.ErrorCodeUtil;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

@Component
class ValidationErrorFactory {

    private final ErrorCodeUtil errorCodeUtil;

    ValidationErrorFactory(ErrorCodeUtil errorCodeUtil) {
        this.errorCodeUtil = errorCodeUtil;
    }

    ValidationError buildError(String errorCode) {
        String errorDescription = errorCodeUtil.getErrorDescription(errorCode);
        return new ValidationError(errorCode, errorDescription);
    }

}
