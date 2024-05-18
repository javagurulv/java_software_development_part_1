package org.javaguru.travel.insurance.core.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.command.TravelGetAllAgreementUuidsCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetAllAgreementUuidsCoreResult;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementEntityRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelGetAllAgreementUuidsServiceImpl
        implements TravelGetAllAgreementUuidsService {
    
    private final AgreementEntityRepository agreementRepository;

    @Override
    public TravelGetAllAgreementUuidsCoreResult getAgreement(TravelGetAllAgreementUuidsCoreCommand command) {
        List<String> agreementUuids = agreementRepository.getAllAgreementUuids();
        return new TravelGetAllAgreementUuidsCoreResult(null, agreementUuids);
    }

}
