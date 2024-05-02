package com.mattae.snl.plugins.ehr.api.extensions;

import com.mattae.snl.plugins.ehr.api.domain.Patient;
import org.pf4j.ExtensionPoint;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Defines information about observation applicable to a patient
 */
public interface ApplicableExtension extends ExtensionPoint {
    /**
     * Returns turn if this observation is applicable to the patient
     * @param patient The patient
     * @return true if it's applicable
     */
    boolean applicableTo(Patient.IdView patient);

    String getName();

    /**
     * Module federation component for editing the observation
     * @return The component ID
     */
    UUID getEditComponent();

    /**
     * Module federation component for viewing the observation data
     * @return The component ID
     */
    default UUID getViewComponent() {
        return null;
    }

    /**
     * The view type
     * @return type
     */
    default String getType() {
        return null;
    }

    default int getOrder() {
        return 100;
    }

    /**
     * Roles that can view this component
     * @return List roles
     */
    default List<String> getRoles() {
        return Collections.emptyList();
    }
}
