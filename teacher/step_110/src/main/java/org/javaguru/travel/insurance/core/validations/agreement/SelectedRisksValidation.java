package org.javaguru.travel.insurance.core.validations.agreement;

import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
class SelectedRisksValidation extends TravelAgreementFieldValidationImpl {

    private final ClassifierValueRepository classifierValueRepository;
    private final ValidationErrorFactory errorFactory;

    SelectedRisksValidation(ClassifierValueRepository classifierValueRepository,
                            ValidationErrorFactory errorFactory) {
        this.classifierValueRepository = classifierValueRepository;
        this.errorFactory = errorFactory;
    }

    @Override
    public List<ValidationError> validateList(TravelCalculatePremiumRequestV1 request) {
        return request.getSelectedRisks() != null
                ? validateSelectedRisks(request)
                : List.of();
    }

    private List<ValidationError> validateSelectedRisks(TravelCalculatePremiumRequestV1 request) {
        return request.getSelectedRisks().stream()
                .map(this::validateRiskIc)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<ValidationError> validateRiskIc(String riskIc) {
        return !existInDatabase(riskIc)
                ? Optional.of(buildValidationError(riskIc))
                : Optional.empty();
    }

    private ValidationError buildValidationError(String riskIc) {
        Placeholder placeholder = new Placeholder("NOT_EXISTING_RISK_TYPE", riskIc);
        return errorFactory.buildError("ERROR_CODE_9", List.of(placeholder));
    }

    private boolean existInDatabase(String riskIc) {
        return classifierValueRepository.findByClassifierTitleAndIc("RISK_TYPE", riskIc).isPresent();
    }

}
