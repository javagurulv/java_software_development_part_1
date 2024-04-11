package org.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.TravelCostCoefficient;
import org.javaguru.travel.insurance.core.repositories.TravelCostCoefficientRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class TravelCostCoefficientCalculator {

    private final TravelCostCoefficientRepository travelCostCoefficientRepository;

    TravelCostCoefficientCalculator(TravelCostCoefficientRepository travelCostCoefficientRepository) {
        this.travelCostCoefficientRepository = travelCostCoefficientRepository;
    }

    BigDecimal calculate(PersonDTO person) {
        return travelCostCoefficientRepository.findCoefficient(person.getTravelCost())
                .map(TravelCostCoefficient::getCoefficient)
                .orElseThrow(() -> new RuntimeException("Travel Cost coefficient not found for travel cost = " + person.getTravelCost()));
    }

}
