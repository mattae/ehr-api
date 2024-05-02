package com.mattae.snl.plugins.ehr.api.extensions.encounter;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.jbella.snl.core.api.services.errors.DataValidationException;
import org.pf4j.ExtensionPoint;

/**
 * Provide validation for an observation type
 */
public interface ObservationValidatorExtension extends ExtensionPoint {
    /**
     * Indicates if this extension handles observation of a type
     * @param type The observation type
     * @return true if it handles this type
     */
    boolean applicableTo(String type);

    /**
     * Throws DataValidationException if provided data fails validation
     * @param data The observation data
     * @throws DataValidationException Exception thrown if there is validation error
     */
    void isValid(JsonNode data) throws DataValidationException;
}
