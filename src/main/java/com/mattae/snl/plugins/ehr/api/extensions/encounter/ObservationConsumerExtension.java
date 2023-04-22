package com.mattae.snl.plugins.ehr.api.extensions.encounter;

import com.mattae.snl.plugins.ehr.api.domain.Encounter;
import com.mattae.snl.plugins.ehr.api.domain.Patient;
import com.mattae.snl.plugins.ehr.api.domain.ReferencedData;
import com.mattae.snl.plugins.ehr.api.extensions.ConsumerExtension;
import io.github.jbella.snl.core.api.domain.Organisation;

import java.util.List;
import java.util.UUID;


public interface ObservationConsumerExtension extends ConsumerExtension {
    default void observationSaved(Organisation.IdView organisation,
                                  Patient.IdView patient,
                                  Encounter.IdView encounter,
                                  ObservationProcessorExtension.ObservationResponse data,
                                  List<ReferencedData.CreateView> referencedData) {
    }

    default void patientSaved(Organisation.IdView organisation,
                              Patient.CreateView patient,
                              List<ReferencedData.CreateView> referencedData) {
    }

    default void observationDeleted(Organisation.IdView organisation, Encounter.IdView encounter, UUID id) {
    }

    default void patientDelete(Organisation.IdView organisation, UUID id) {
    }

    default void deletedByEncounter(Organisation.IdView organisation, Encounter.IdView encounter) {
    }
}
