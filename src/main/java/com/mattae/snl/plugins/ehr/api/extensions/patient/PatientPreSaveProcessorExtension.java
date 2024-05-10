package com.mattae.snl.plugins.ehr.api.extensions.patient;

import com.mattae.snl.plugins.ehr.api.extensions.Patient;
import org.pf4j.ExtensionPoint;

public interface PatientPreSaveProcessorExtension extends ExtensionPoint {
    Patient process(Patient patient);
}
