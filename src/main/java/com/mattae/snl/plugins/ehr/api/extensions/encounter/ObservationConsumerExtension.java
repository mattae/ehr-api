package com.mattae.snl.plugins.ehr.api.extensions.encounter;

import com.mattae.snl.plugins.ehr.api.domain.Encounter;
import com.mattae.snl.plugins.ehr.api.domain.Patient;
import com.mattae.snl.plugins.ehr.api.domain.ReferencedData;
import com.mattae.snl.plugins.ehr.api.extensions.ConsumerExtension;
import io.github.jbella.snl.core.api.domain.Organisation;

import java.util.List;
import java.util.UUID;

/**
 * Implemented to respond to CRUD states of an Observation
 */
public interface ObservationConsumerExtension extends ConsumerExtension {
    /**
     * Called when any observation has been saved
     *
     * @param organisation   Organisation
     * @param patient        Patient
     * @param encounter      Encounter
     * @param data           Observation data
     * @param referencedData Additional data accompanying the observation
     */
    default void observationSaved(Organisation.IdView organisation,
                                  Patient.IdView patient,
                                  Encounter.IdView encounter,
                                  ObservationProcessorExtension.ObservationResponse data,
                                  List<ReferencedData.CreateView> referencedData) {
    }

    /**
     * Called when a patient has been successfully saved
     *
     * @param organisation   Organisation
     * @param patient        Patient
     * @param referencedData Additional data collected with the patient
     */
    default void patientSaved(Organisation.IdView organisation,
                              Patient.CreateView patient,
                              List<ReferencedData.CreateView> referencedData) {
    }

    /**
     * Called when an Observation has been deleted
     *
     * @param organisation Organisation
     * @param encounter    Encounter
     * @param id           The observation ID
     */
    default void observationDeleted(Organisation.IdView organisation, Encounter.IdView encounter, UUID id) {
    }

    /**
     * Called when a patient has been deleted
     *
     * @param organisation Organisation
     * @param id           The patient ID
     */
    default void patientDelete(Organisation.IdView organisation, UUID id) {
    }

    /**
     * Delete information by Encounter
     *
     * @param organisation Organisation
     * @param encounter    Encounter
     */
    default void deletedByEncounter(Organisation.IdView organisation, Encounter.IdView encounter) {
    }
}
