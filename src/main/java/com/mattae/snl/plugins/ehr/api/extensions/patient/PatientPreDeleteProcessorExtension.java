package com.mattae.snl.plugins.ehr.api.extensions.patient;

import org.pf4j.ExtensionPoint;

import java.util.UUID;

public interface PatientPreDeleteProcessorExtension extends ExtensionPoint {
    void deleteForPatient(UUID id);
}
