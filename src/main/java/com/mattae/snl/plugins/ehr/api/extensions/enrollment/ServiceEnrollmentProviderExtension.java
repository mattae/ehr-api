package com.mattae.snl.plugins.ehr.api.extensions.enrollment;

import com.mattae.snl.plugins.ehr.api.domain.Patient;
import com.mattae.snl.plugins.ehr.api.extensions.ApplicableExtension;
import io.github.jbella.snl.core.api.domain.Organisation;

import java.util.UUID;

public interface ServiceEnrollmentProviderExtension extends ApplicableExtension {
    default boolean canBeConcluded() {
        return false;
    }

    default boolean canBeConcluded(Organisation.IdView organisation, Patient.IdView patient, UUID id) {
        return false;
    }

    default UUID getConclusionComponent() {
        return null;
    }
}
