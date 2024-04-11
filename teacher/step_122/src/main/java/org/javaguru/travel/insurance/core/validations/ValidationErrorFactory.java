package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.util.ErrorCodeUtil;
import org.javaguru.travel.insurance.core.util.Placeholder;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidationErrorFactory {

    private final ErrorCodeUtil errorCodeUtil;

    ValidationErrorFactory(ErrorCodeUtil errorCodeUtil) {
        this.errorCodeUtil = errorCodeUtil;
    }

    public ValidationErrorDTO buildError(String errorCode) {
        String errorDescription = errorCodeUtil.getErrorDescription(errorCode);
        return new ValidationErrorDTO(errorCode, errorDescription);
    }

    public ValidationErrorDTO buildError(String errorCode, List<Placeholder> placeholders) {
        String errorDescription = errorCodeUtil.getErrorDescription(errorCode, placeholders);
        return new ValidationErrorDTO(errorCode, errorDescription);
    }

}
