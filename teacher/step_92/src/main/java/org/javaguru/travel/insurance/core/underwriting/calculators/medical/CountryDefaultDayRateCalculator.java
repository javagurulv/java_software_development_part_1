package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.domain.CountryDefaultDayRate;
import org.javaguru.travel.insurance.core.repositories.CountryDefaultDayRateRepository;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class CountryDefaultDayRateCalculator {

    private final CountryDefaultDayRateRepository countryDefaultDayRateRepository;

    CountryDefaultDayRateCalculator(CountryDefaultDayRateRepository countryDefaultDayRateRepository) {
        this.countryDefaultDayRateRepository = countryDefaultDayRateRepository;
    }

    BigDecimal calculate(TravelCalculatePremiumRequest request) {
        return countryDefaultDayRateRepository.findByCountryIc(request.getCountry())
                .map(CountryDefaultDayRate::getDefaultDayRate)
                .orElseThrow(() -> new RuntimeException("Country day rate not found by country id = " + request.getCountry()));
    }

}
