package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.TMAgeCoefficient;
import org.javaguru.travel.insurance.core.repositories.TMAgeCoefficientRepository;
import org.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgeCoefficientCalculatorTest {

    @Mock private DateTimeUtil dateTimeUtil;
    @Mock private TMAgeCoefficientRepository ageCoefficientRepository;

    @InjectMocks
    private AgeCoefficientCalculator calculator;

    private PersonDTO person;

    @BeforeEach
    void setUp() {
        person = new PersonDTO();
        person.setPersonBirthDate(Date.from(LocalDate.of(1990, 1, 1)
                .atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    @Test
    void shouldFindCoefficientWhenAgeCoefficientExists() {
        ReflectionTestUtils.setField(calculator, "medicalRiskAgeCoefficientEnabled", true);
        LocalDate currentDate = LocalDate.of(2023, 3, 27);
        int age = 33;
        BigDecimal expectedCoefficient = BigDecimal.valueOf(1.2);

        when(dateTimeUtil.getCurrentDateTime()).thenReturn(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        TMAgeCoefficient ageCoefficient = mock(TMAgeCoefficient.class);
        when(ageCoefficient.getCoefficient()).thenReturn(expectedCoefficient);
        when(ageCoefficientRepository.findCoefficient(age)).thenReturn(Optional.of(ageCoefficient));

        BigDecimal result = calculator.calculate(person);

        assertEquals(expectedCoefficient, result);
    }

    @Test
    void shouldThrowExceptionWhenAgeCoefficientNotFound() {
        ReflectionTestUtils.setField(calculator, "medicalRiskAgeCoefficientEnabled", true);
        LocalDate currentDate = LocalDate.of(2023, 3, 27);
        int age = 33;

        when(dateTimeUtil.getCurrentDateTime()).thenReturn(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        when(ageCoefficientRepository.findCoefficient(age)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> calculator.calculate(person));

        assertEquals("Age coefficient not found for age = " + age, exception.getMessage());
    }

}