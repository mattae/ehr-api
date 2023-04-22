package com.mattae.snl.plugins.ehr.api.extensions.encounter;

import com.fasterxml.jackson.databind.JsonNode;
import com.mattae.snl.plugins.ehr.api.domain.Encounter;
import com.mattae.snl.plugins.ehr.api.extensions.model.RequestPayload;
import io.github.jbella.snl.core.api.domain.Organisation;
import org.pf4j.ExtensionPoint;

import java.util.List;
import java.util.UUID;

public interface ObservationProcessorExtension extends ExtensionPoint {
    boolean applicableTo(String type);

    default Integer getOrder() {
        return 1;
    }

    ObservationResponse save(RequestPayload<?> payload, Organisation.IdView organisation);

    List<ObservationResponse> getByEncounter(Encounter.IdView encounter, Organisation.IdView organisation);

    ObservationResponse getById(UUID id, Organisation.IdView organisation);

    void deleteById(UUID id, Encounter.IdView encounter, Organisation.IdView organisation);

    void deleteByEncounter(Encounter.IdView encounter, Organisation.IdView organisation);

    record ObservationResponse(UUID id, String type, JsonNode data) {
    }
}
