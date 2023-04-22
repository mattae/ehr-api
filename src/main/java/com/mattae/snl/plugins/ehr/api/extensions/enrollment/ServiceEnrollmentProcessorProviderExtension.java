package com.mattae.snl.plugins.ehr.api.extensions.enrollment;

import com.fasterxml.jackson.databind.JsonNode;
import com.mattae.snl.plugins.ehr.api.domain.Patient;
import io.github.jbella.snl.core.api.domain.Organisation;
import org.pf4j.ExtensionPoint;

import java.time.LocalDate;
import java.util.UUID;

public interface ServiceEnrollmentProcessorProviderExtension extends ExtensionPoint {
    boolean applicableTo(String name);

    default void processSave(Organisation.IdView organisation, Patient.IdView patient, LocalDate start, JsonNode data) {

    }

    default void processDelete(Organisation.IdView organisation, Patient.IdView patient, UUID id) {
    }

    default void processConclude(Organisation.IdView organisation, Patient.IdView patient, LocalDate end, JsonNode data) {

    }
}
