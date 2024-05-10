package com.mattae.snl.plugins.ehr.api.extensions.patient;

import com.mattae.snl.plugins.ehr.api.extensions.PatientExt;
import org.pf4j.ExtensionPoint;

public interface PatientPreSaveProcessorExtension extends ExtensionPoint {
    PatientExt process(PatientExt patient);
}
