package org.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.domain.TCCountrySafetyRatingCoefficient;
import org.javaguru.travel.insurance.core.repositories.TCCountrySafetyRatingCoefficientRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class TCCountrySafetyRatingCoefficientCalculator {

    private final TCCountrySafetyRatingCoefficientRepository countrySafetyRatingCoefficientRepository;

    TCCountrySafetyRatingCoefficientCalculator(TCCountrySafetyRatingCoefficientRepository countrySafetyRatingCoefficientRepository) {
        this.countrySafetyRatingCoefficientRepository = countrySafetyRatingCoefficientRepository;
    }

    BigDecimal calculate(AgreementDTO agreement) {
        return countrySafetyRatingCoefficientRepository.findByCountryIc(agreement.getCountry())
                .map(TCCountrySafetyRatingCoefficient::getCoefficient)
                .orElseThrow(() -> new RuntimeException("Country safety rating coefficient not found by country id = " + agreement.getCountry()));
    }

}
