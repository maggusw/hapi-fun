package com.example.fhir_subscription_challenge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api")
public class SubscriptionController {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

    // this is interesting
    // Firely server will compose the URL like this
    // [base]/ResourceType/{id}
    // HAPI composes it likes this:
    // [base]/ResourceType/{id}/_history/{anotherId}

    @RequestMapping(value = "/receive/Patient/{id}/**",
            method = RequestMethod.PUT,
            consumes = {"application/fhir+json"},
            produces = {"text/plain"})
    @ResponseStatus(HttpStatus.OK)
    public void receivePatient(@PathVariable int id, @RequestBody String fhirPatient) {

        // essentially we receive a string as body here
        // I decided against pretty-printing and parsing the response here
        // as the requirement was to receive and log the Patient that has been subscribed to

        logger.info("Received FHIR resource with id: " + id);
        logger.info(fhirPatient);

    }
}
