package com.mattae.snl.plugins.ehr.api.extensions.encounter;

import com.fasterxml.jackson.databind.JsonNode;
import com.mattae.snl.plugins.ehr.api.domain.Encounter;
import com.mattae.snl.plugins.ehr.api.extensions.model.RequestPayload;
import io.github.jbella.snl.core.api.domain.Organisation;
import org.pf4j.ExtensionPoint;

import java.util.List;
import java.util.UUID;

/**
 * Entry point for processing of observation data collect from the patient facesheet
 */
public interface ObservationProcessorExtension extends ExtensionPoint {
    /**
     * Indicates if this processor handles observation of a type
     * @param type The observation type
     * @return true if it handles this type
     */
    boolean applicableTo(String type);

    /**
     * If multiple processors exist for a particular type, the one with the highest order is selected
     * @return the processor order
     */
    default Integer getOrder() {
        return 1;
    }

    /**
     * Save observation data
     * @param payload The data payload
     * @param organisation Current organisation information
     * @return Saved response
     */
    ObservationResponse save(RequestPayload<?> payload, Organisation.IdView organisation);

    /**
     * Get observation type by encounter
     * @param encounter The encounter
     * @param organisation Current organisation
     * @return List of observation responses
     */
    List<ObservationResponse> getByEncounter(Encounter.IdView encounter, Organisation.IdView organisation);

    /**
     * Get an observation data by ID
     * @param id The ID
     * @param organisation Current organisation
     * @return Observation response
     */
    ObservationResponse getById(UUID id, Organisation.IdView organisation);

    void deleteById(UUID id, Encounter.IdView encounter, Organisation.IdView organisation);

    void deleteByEncounter(Encounter.IdView encounter, Organisation.IdView organisation);

    record ObservationResponse(UUID id, String type, JsonNode data) {
    }
}
