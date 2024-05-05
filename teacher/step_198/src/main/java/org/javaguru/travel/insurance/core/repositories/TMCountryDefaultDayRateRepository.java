package org.javaguru.travel.insurance.core.repositories;

import org.javaguru.travel.insurance.core.domain.TMCountryDefaultDayRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TMCountryDefaultDayRateRepository
        extends JpaRepository<TMCountryDefaultDayRate, Long> {

    Optional<TMCountryDefaultDayRate> findByCountryIc(String countryIc);

}
