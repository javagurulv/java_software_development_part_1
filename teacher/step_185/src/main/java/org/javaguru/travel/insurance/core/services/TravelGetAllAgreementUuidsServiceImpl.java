package org.javaguru.travel.insurance.core.services;

import org.javaguru.travel.insurance.core.api.command.TravelGetAllAgreementUuidsCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetAllAgreementUuidsCoreResult;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementEntityRepository;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
class TravelGetAllAgreementUuidsServiceImpl
        implements TravelGetAllAgreementUuidsService {

    private final AgreementEntityRepository agreementRepository;

    TravelGetAllAgreementUuidsServiceImpl(AgreementEntityRepository agreementRepository) {
        this.agreementRepository = agreementRepository;
    }

    @Override
    public TravelGetAllAgreementUuidsCoreResult getAgreement(TravelGetAllAgreementUuidsCoreCommand command) {
        List<String> agreementUuids = agreementRepository.getAllAgreementUuids();
        return new TravelGetAllAgreementUuidsCoreResult(null, agreementUuids);
    }

}
