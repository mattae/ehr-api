package com.mattae.snl.plugins.ehr.api.extensions.data;

import com.fasterxml.jackson.databind.JsonNode;
import org.pf4j.ExtensionPoint;

import java.util.UUID;

public interface ReferencedDataValidatorExtension extends ExtensionPoint {
    boolean applicableTo(String type);

    boolean isValid(UUID referenceKy, JsonNode data);
}
