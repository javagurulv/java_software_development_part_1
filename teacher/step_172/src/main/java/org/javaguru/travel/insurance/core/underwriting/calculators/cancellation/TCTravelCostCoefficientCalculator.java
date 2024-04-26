package org.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.TCTravelCostCoefficient;
import org.javaguru.travel.insurance.core.repositories.TCTravelCostCoefficientRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TCTravelCostCoefficientCalculator {

    private final TCTravelCostCoefficientRepository TCTravelCostCoefficientRepository;

    BigDecimal calculate(PersonDTO person) {
        return TCTravelCostCoefficientRepository.findCoefficient(person.getTravelCost())
                .map(TCTravelCostCoefficient::getCoefficient)
                .orElseThrow(() -> new RuntimeException("Travel Cost coefficient not found for travel cost = " + person.getTravelCost()));
    }

}
