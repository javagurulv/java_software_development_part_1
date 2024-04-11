package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.domain.CountryDefaultDayRate;
import org.javaguru.travel.insurance.core.repositories.CountryDefaultDayRateRepository;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class CountryDefaultDayRateCalculator {

    private final CountryDefaultDayRateRepository countryDefaultDayRateRepository;

    CountryDefaultDayRateCalculator(CountryDefaultDayRateRepository countryDefaultDayRateRepository) {
        this.countryDefaultDayRateRepository = countryDefaultDayRateRepository;
    }

    BigDecimal calculate(AgreementDTO agreement) {
        return countryDefaultDayRateRepository.findByCountryIc(agreement.getCountry())
                .map(CountryDefaultDayRate::getDefaultDayRate)
                .orElseThrow(() -> new RuntimeException("Country day rate not found by country id = " + agreement.getCountry()));
    }

}
