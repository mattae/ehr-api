package com.mattae.snl.plugins.ehr.api.extensions.patient;

import com.mattae.snl.plugins.ehr.api.extensions.Patient;
import org.pf4j.ExtensionPoint;

public interface PatientPostSaveProcessorExtension extends ExtensionPoint {
    Patient process(Patient patient);
}
