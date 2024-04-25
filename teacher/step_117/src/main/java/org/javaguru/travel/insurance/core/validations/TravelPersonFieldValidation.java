package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;

import java.util.List;
import java.util.Optional;

public interface TravelPersonFieldValidation {

    Optional<ValidationError> validate(TravelCalculatePremiumRequest request);

    List<ValidationError> validateList(TravelCalculatePremiumRequest request);

}
