package org.javaguru.travel.insurance.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "travel_medical_country_default_day_rate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TMCountryDefaultDayRate {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country_ic", nullable = false)
    private String countryIc;

    @Column(name = "default_day_rate", precision = 10, scale = 2, nullable = false)
    private BigDecimal defaultDayRate;

}
