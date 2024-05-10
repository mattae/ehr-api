package com.mattae.snl.plugins.ehr.api.extensions.checklist;

import com.fasterxml.jackson.databind.JsonNode;
import com.mattae.snl.plugins.ehr.api.extensions.PatientExt;
import io.github.jbella.snl.core.api.extensions.OrderedExtension;
import io.github.jbella.snl.core.api.services.errors.DataValidationException;

import java.util.UUID;

public interface ChecklistProcessorExtension extends OrderedExtension {
    boolean isApplicable(String checklistType);

    default void validate(PatientExt patient, JsonNode data) throws DataValidationException {
    }

    default void save(UUID encounterId, PatientExt patient, JsonNode data) {
    }

    default void delete(UUID encounterId) {

    }
}
