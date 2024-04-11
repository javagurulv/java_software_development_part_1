package org.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.TCTravelCostCoefficient;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class TCTravelCostCoefficientCalculator {

    private final TCTravelCostCoefficientRepository tcTravelCostCoefficientRepository;

    TCTravelCostCoefficientCalculator(TCTravelCostCoefficientRepository tcTravelCostCoefficientRepository) {
        this.tcTravelCostCoefficientRepository = tcTravelCostCoefficientRepository;
    }

    BigDecimal calculate(PersonDTO person) {
        return TCTravelCostCoefficientRepository.findCoefficient(person.getTravelCost())
                .map(TCTravelCostCoefficient::getCoefficient)
                .orElseThrow(() -> new RuntimeException("Travel Cost coefficient not found for travel cost = " + person.getTravelCost()));
    }

}
