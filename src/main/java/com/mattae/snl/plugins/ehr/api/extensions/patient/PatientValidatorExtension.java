package com.mattae.snl.plugins.ehr.api.extensions.patient;

import com.mattae.snl.plugins.ehr.api.domain.Patient;
import org.pf4j.ExtensionPoint;


public interface PatientValidatorExtension extends ExtensionPoint {
    boolean isValid(Patient.CreateView patient);
}
