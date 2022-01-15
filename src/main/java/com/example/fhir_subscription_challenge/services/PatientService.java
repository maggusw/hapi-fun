package com.example.fhir_subscription_challenge.services;
import org.hl7.fhir.r4.model.*;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    public PatientService() {}

    public Patient CreatePatient(String familyName, String givenName) {
        Patient patient = new Patient();

        // some data is taken from the MII Patient
        // conforming to the profile would be a bit
        // too much as it has often has 1..*

        Identifier identifier = new Identifier();
        identifier.setUse(Identifier.IdentifierUse.USUAL);

        Coding coding = new Coding();
        coding.setSystem("http://terminology.hl7.org/CodeSystem/v2-0203");
        coding.setCode("MR");
        CodeableConcept cc = new CodeableConcept(coding);
        identifier.setType(cc);

        identifier.setSystem("https://www.medizininformatik-initiative.de/fhir/core/NamingSystem/patient-identifier");
        identifier.setValue("42285243");

        Reference assigner = new Reference();
        assigner.setDisplay("Charité – Universitätsmedizin Berlin");
        assigner.setIdentifier(new Identifier().
                setSystem("http://fhir.de/NamingSystem/arge-ik/iknr").setValue("261101015"));
        identifier.setAssigner(assigner);

        HumanName name = new HumanName();
        name.setUse(HumanName.NameUse.OFFICIAL);
        name.setFamily(familyName);
        name.addGiven(givenName);

        patient.addIdentifier(identifier);
        patient.addName(name);

        return patient;
    }
}
