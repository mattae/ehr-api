package com.mattae.snl.plugins.ehr.api.extensions.encounter;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.jbella.snl.core.api.services.errors.DataValidationException;
import org.pf4j.ExtensionPoint;

public interface ObservationValidatorExtension extends ExtensionPoint {
    boolean applicableTo(String type);

    void isValid(JsonNode data) throws DataValidationException;
}
