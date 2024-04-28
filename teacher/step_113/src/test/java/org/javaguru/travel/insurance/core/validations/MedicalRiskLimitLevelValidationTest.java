package org.javaguru.travel.insurance.core.validations;

import org.javaguru.travel.insurance.core.domain.ClassifierValue;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.validations.MedicalRiskLimitLevelValidation;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicalRiskLimitLevelValidationTest {

    @Mock private ClassifierValueRepository classifierValueRepository;
    @Mock private ValidationErrorFactory errorFactory;

    @InjectMocks
    private MedicalRiskLimitLevelValidation validation;

    @Test
    public void shouldNotReturnErrorWhenMedicalRiskLimitLevelNotEnabled() {
        ReflectionTestUtils.setField(validation, "medicalRiskLimitLevelEnabled", Boolean.FALSE);
        TravelCalculatePremiumRequest request = mock(TravelCalculatePremiumRequest.class);
        Optional<ValidationError> validationErrorOpt = validation.validate(request);
        assertTrue(validationErrorOpt.isEmpty());
        verifyNoInteractions(classifierValueRepository, errorFactory);
    }

    @Test
    public void shouldNotReturnErrorWhenNotContainTravelMedicalRisk() {
        ReflectionTestUtils.setField(validation, "medicalRiskLimitLevelEnabled", Boolean.TRUE);
        TravelCalculatePremiumRequest request = mock(TravelCalculatePremiumRequest.class);
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_EVACUATION"));
        Optional<ValidationError> validationErrorOpt = validation.validate(request);
        assertTrue(validationErrorOpt.isEmpty());
        verifyNoInteractions(classifierValueRepository, errorFactory);
    }

    @Test
    public void shouldNotReturnErrorWhenMedicalRiskLimitLevelIsNull() {
        ReflectionTestUtils.setField(validation, "medicalRiskLimitLevelEnabled", Boolean.TRUE);
        TravelCalculatePremiumRequest request = mock(TravelCalculatePremiumRequest.class);
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        when(request.getMedicalRiskLimitLevel()).thenReturn(null);
        Optional<ValidationError> validationErrorOpt = validation.validate(request);
        assertTrue(validationErrorOpt.isEmpty());
        verifyNoInteractions(classifierValueRepository, errorFactory);
    }

    @Test
    public void shouldNotReturnErrorWhenMedicalRiskLimitLevelIsBlank() {
        ReflectionTestUtils.setField(validation, "medicalRiskLimitLevelEnabled", Boolean.TRUE);
        TravelCalculatePremiumRequest request = mock(TravelCalculatePremiumRequest.class);
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        when(request.getMedicalRiskLimitLevel()).thenReturn("");
        Optional<ValidationError> validationErrorOpt = validation.validate(request);
        assertTrue(validationErrorOpt.isEmpty());
        verifyNoInteractions(classifierValueRepository, errorFactory);
    }

    @Test
    public void shouldNotReturnErrorWhenMedicalRiskLimitLevelExistInDb() {
        ReflectionTestUtils.setField(validation, "medicalRiskLimitLevelEnabled", Boolean.TRUE);
        TravelCalculatePremiumRequest request = mock(TravelCalculatePremiumRequest.class);
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        when(request.getMedicalRiskLimitLevel()).thenReturn("LEVEL_10000");
        ClassifierValue classifierValue = mock(ClassifierValue.class);
        when(classifierValueRepository.findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", "LEVEL_10000"))
                .thenReturn(Optional.of(classifierValue));
        Optional<ValidationError> validationErrorOpt = validation.validate(request);
        assertTrue(validationErrorOpt.isEmpty());
        verifyNoInteractions(errorFactory);
    }

    @Test
    public void shouldReturnError() {
        ReflectionTestUtils.setField(validation, "medicalRiskLimitLevelEnabled", Boolean.TRUE);
        TravelCalculatePremiumRequest request = mock(TravelCalculatePremiumRequest.class);
        when(request.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        when(request.getMedicalRiskLimitLevel()).thenReturn("LEVEL_10000");
        when(classifierValueRepository.findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL", "LEVEL_10000"))
                .thenReturn(Optional.empty());
        ValidationError validationError = mock(ValidationError.class);
        when(errorFactory.buildError("ERROR_CODE_14")).thenReturn(validationError);
        Optional<ValidationError> validationErrorOpt = validation.validate(request);
        assertTrue(validationErrorOpt.isPresent());
        assertSame(validationError, validationErrorOpt.get());
    }

}