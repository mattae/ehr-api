package com.mattae.snl.plugins.ehr.api.extensions;

import com.mattae.snl.plugins.ehr.api.domain.Patient;
import org.pf4j.ExtensionPoint;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public interface ApplicableExtension extends ExtensionPoint {
    boolean applicableTo(Patient.IdView patient);

    String getName();

    UUID getEditComponent();

    default UUID getViewComponent() {
        return null;
    }

    default String getType() {
        return null;
    }

    default int getOrder() {
        return 100;
    }

    default List<String> getRoles() {
        return Collections.emptyList();
    }
}
