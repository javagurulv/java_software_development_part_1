package org.javaguru.travel.insurance.dto.v1;

import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.dto.RiskPremium;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoV1Converter {

    public TravelCalculatePremiumCoreCommand buildCoreCommand(TravelCalculatePremiumRequestV1 request) {
        AgreementDTO agreement = buildAgreement(request);
        return new TravelCalculatePremiumCoreCommand(agreement);
    }

    public TravelCalculatePremiumResponseV1 buildResponse(TravelCalculatePremiumCoreResult coreResult) {
        return coreResult.hasErrors()
                ? buildResponseWithErrors(coreResult.getErrors())
                : buildSuccessfulResponse(coreResult);
    }

    private TravelCalculatePremiumResponseV1 buildResponseWithErrors(List<ValidationErrorDTO> coreErrors) {
        List<ValidationError> errors = transformValidationErrorsToV1(coreErrors);
        return new TravelCalculatePremiumResponseV1(errors);
    }

    private List<ValidationError> transformValidationErrorsToV1(List<ValidationErrorDTO> coreErrors) {
        return coreErrors.stream()
                .map(error -> new ValidationError(error.getErrorCode(), error.getDescription()))
                .collect(Collectors.toList());
    }

    private TravelCalculatePremiumResponseV1 buildSuccessfulResponse(TravelCalculatePremiumCoreResult coreResult) {
        AgreementDTO agreement = coreResult.getAgreement();
        TravelCalculatePremiumResponseV1 response = new TravelCalculatePremiumResponseV1();
        response.setPersonFirstName(agreement.getPersons().get(0).getPersonFirstName());
        response.setPersonLastName(agreement.getPersons().get(0).getPersonLastName());
        response.setPersonBirthDate(agreement.getPersons().get(0).getPersonBirthDate());
        response.setAgreementDateFrom(agreement.getAgreementDateFrom());
        response.setAgreementDateTo(agreement.getAgreementDateTo());
        response.setCountry(agreement.getCountry());
        response.setMedicalRiskLimitLevel(agreement.getMedicalRiskLimitLevel());
        response.setAgreementPremium(agreement.getAgreementPremium());

        PersonDTO person = agreement.getPersons().get(0);
        List<RiskPremium> riskPremiums = person.getRisks().stream()
                .map(riskDTO -> new RiskPremium(riskDTO.getRiskIc(), riskDTO.getPremium()))
                .toList();
        response.setRisks(riskPremiums);

        return response;
    }

    private PersonDTO buildPerson(TravelCalculatePremiumRequestV1 request) {
        PersonDTO person = new PersonDTO();
        person.setPersonFirstName(request.getPersonFirstName());
        person.setPersonLastName(request.getPersonLastName());
        person.setPersonBirthDate(request.getPersonBirthDate());
        return person;
    }

    private AgreementDTO buildAgreement(TravelCalculatePremiumRequestV1 request) {
        AgreementDTO agreement = new AgreementDTO();
        agreement.setAgreementDateFrom(request.getAgreementDateFrom());
        agreement.setAgreementDateTo(request.getAgreementDateTo());
        agreement.setCountry(request.getCountry());
        agreement.setMedicalRiskLimitLevel(request.getMedicalRiskLimitLevel());
        agreement.setSelectedRisks(request.getSelectedRisks());

        PersonDTO person = buildPerson(request);
        agreement.setPersons(List.of(person));

        return agreement;
    }


}
