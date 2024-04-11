package org.javaguru.travel.insurance.core.services;

import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreResult;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validations.TravelAgreementUuidValidator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
class TravelGetAgreementServiceImpl implements TravelGetAgreementService {

    private final TravelAgreementUuidValidator agreementUuidValidator;
    private final AgreementDTOLoader agreementDTOLoader;

    TravelGetAgreementServiceImpl(TravelAgreementUuidValidator agreementUuidValidator,
                                  AgreementDTOLoader agreementDTOLoader) {
        this.agreementUuidValidator = agreementUuidValidator;
        this.agreementDTOLoader = agreementDTOLoader;
    }

    @Override
    public TravelGetAgreementCoreResult getAgreement(TravelGetAgreementCoreCommand command) {
        List<ValidationErrorDTO> errors = agreementUuidValidator.validate(command.getUuid());
        return errors.isEmpty()
                ? buildResponse(command.getUuid())
                : buildResponse(errors);
    }

    private TravelGetAgreementCoreResult buildResponse(List<ValidationErrorDTO> errors) {
        return new TravelGetAgreementCoreResult(errors);
    }

    private TravelGetAgreementCoreResult buildResponse(String agreementUuid) {
        TravelGetAgreementCoreResult coreResult = new TravelGetAgreementCoreResult();
        coreResult.setAgreement(agreementDTOLoader.load(agreementUuid));
        return coreResult;
    }

}
