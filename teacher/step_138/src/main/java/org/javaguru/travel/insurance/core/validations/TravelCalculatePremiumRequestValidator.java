package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.dto.ValidationError;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;

import java.util.List;

public interface TravelCalculatePremiumRequestValidator {

    List<ValidationError> validate(TravelCalculatePremiumRequestV1 request);

}
