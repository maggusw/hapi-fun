package com.example.fhir_subscription_challenge.controller;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.exceptions.FhirClientConnectionException;
import com.example.fhir_subscription_challenge.services.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class PatientController {

    @Autowired
    private PatientService patientService;

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @RequestMapping(value = "/create/Patient",
            method = RequestMethod.POST,
            consumes = {"text/plain"},
            produces = {"text/plain"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createPatient(@RequestParam String familyName, @RequestParam String givenName) {
        try {
            FhirContext ctx = FhirContext.forR4();
            var client = ctx.newRestfulGenericClient("http://localhost:8090/fhir/");

            var patient = patientService.CreatePatient(familyName, givenName);
            MethodOutcome result = client.create().resource(patient).prettyPrint().encodedJson().execute();

            logger.info(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(patient));
            logger.info("Created Patient: %s".formatted(result.getId().getValue()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (FhirClientConnectionException exception) {
            logger.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_GATEWAY);
         }
    }
}
