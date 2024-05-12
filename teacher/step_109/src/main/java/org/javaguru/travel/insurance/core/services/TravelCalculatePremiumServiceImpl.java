package org.javaguru.travel.insurance.core.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.underwriting.TravelPremiumCalculationResult;
import org.javaguru.travel.insurance.core.underwriting.TravelPremiumUnderwriting;
import org.javaguru.travel.insurance.core.validations.TravelCalculatePremiumRequestValidator;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumResponse;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelCalculatePremiumServiceImpl implements TravelCalculatePremiumService {

    private final TravelCalculatePremiumRequestValidator requestValidator;
    private final TravelPremiumUnderwriting premiumUnderwriting;

    @Override
    public TravelCalculatePremiumResponse calculatePremium(TravelCalculatePremiumRequest request) {
        List<ValidationError> errors = requestValidator.validate(request);
        return errors.isEmpty()
                ? buildResponse(request, premiumUnderwriting.calculatePremium(request))
                : buildResponse(errors);
    }

    private TravelCalculatePremiumResponse buildResponse(List<ValidationError> errors) {
        return new TravelCalculatePremiumResponse(errors);
    }

    private TravelCalculatePremiumResponse buildResponse(TravelCalculatePremiumRequest request,
                                                         TravelPremiumCalculationResult premiumCalculationResult) {
        TravelCalculatePremiumResponse response = new TravelCalculatePremiumResponse();
        response.setPersonFirstName(request.getPersonFirstName());
        response.setPersonLastName(request.getPersonLastName());
        response.setPersonBirthDate(request.getPersonBirthDate());
        response.setAgreementDateFrom(request.getAgreementDateFrom());
        response.setAgreementDateTo(request.getAgreementDateTo());
        response.setCountry(request.getCountry());
        response.setAgreementPremium(premiumCalculationResult.totalPremium());
        response.setRisks(premiumCalculationResult.riskPremiums());
        return response;
    }

}
