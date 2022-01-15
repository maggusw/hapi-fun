package com.example.fhir_subscription_challenge.services;
import org.hl7.fhir.r4.model.Patient;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class PatientServiceTest {


    @Test
    public void PatientService_Valid_PatientWithName_Success() {
        PatientService patientService = new PatientService();
        Patient patient = patientService.CreatePatient("Bunny", "Bugs");

        assertThat(patient).isNotNull();
        assertThat(patient.hasName()).isTrue();
        assertThat(patient.getNameFirstRep().getGivenAsSingleString()).contains("Bugs");
        assertThat(patient.getNameFirstRep().getFamily()).contains("Bunny");
    }

    @Test
    public void PatientService_Valid_PatientWithIdentifier_Success() {
        PatientService patientService = new PatientService();
        Patient patient = patientService.CreatePatient("E", "Wall");

        assertThat(patient).isNotNull();
        assertThat(patient.hasIdentifier()).isTrue();

        var identifier = patient.getIdentifierFirstRep();

        // we hardcode the following values, they should be present
        assertThat(identifier.getValue()).isEqualTo("42285243");
        assertThat(identifier.getSystem()).
                isEqualTo("https://www.medizininformatik-initiative.de/fhir/core/NamingSystem/patient-identifier");
        assertThat(identifier.getAssigner().getDisplay()).contains("Berlin");
        assertThat(identifier.getType().getCodingFirstRep().getCode()).isEqualTo("MR");
    }

    @Test
    public void PatientService_MissingInput_Success() {
        PatientService patientService = new PatientService();
        Patient patient = patientService.CreatePatient(null, null);

        // it should be there but be empty
        // according to cardinality in base profile this is still valid
        // however doesn't make a whole lot of sense
        assertThat(patient.hasName()).isTrue();
        assertThat(patient.getNameFirstRep().getGivenAsSingleString()).isEmpty();
        assertThat(patient.getNameFirstRep().getFamily()).isNull();
    }
}
