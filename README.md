# Subscription challenge

This is a simple example of an REST API that has two endpoints:

1.

```http request
/api/create/Patient
```

This takes two parameters:

1. `familyName` 
2. `givenName`

There are some hard coded values taken from the MII, see `PatientService` for further details.

This will then send the created Patient resource to the HAPI server configured in the controller.

This endpoint consumes and produces `text/plain` and upon success will return a 201. Supported method is `POST`.

```http request
/api/receive/Patient/{id}
```

This endpoint serves to accept resources that are sent from the FHIR server.

As such it accepts `application/fhir+json` with the corresponding `PUT`-method.

As `@RequestBody` it accepts a string.

A subscription triggering the latter endpoint could look like so:

```json
{
  "resourceType": "Subscription",
  "criteria": "Patient?",
  "status": "requested",
  "channel": {
    "type": "rest-hook",
    "endpoint": "http://endpointURI:8080/api/receive/Patient/",
    "payload": "application/json"
  }
}
```

HAPI will complain if one puts just `DomainResource` as criteria, hence the `?`. 